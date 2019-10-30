package com.charityapp.userappms.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charityapp.userappms.dto.ForgotPasswordDTO;
import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.MailDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.Admin;
import com.charityapp.userappms.repository.AdminRepository;
import com.charityapp.userappms.util.MessageConstant;
import com.charityapp.userappms.validator.AdminValidator;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepoObj;
	@Autowired 
	AdminValidator validator;
	@Autowired
	private MailService mailService;
	
	public Admin adminLogin(final LoginDTO loginDTO) throws ServiceException {
		Admin adminResponseObj = null;
		try {
			String email = loginDTO.getEmail();
			String password = loginDTO.getPassword();
			validator.loginValidator(loginDTO);
			adminResponseObj = adminRepoObj.findByEmailAndPassword(email, password);
			if(adminResponseObj == null)
			{
				throw new ServiceException(MessageConstant.INVALID_CREDENTIAL);
			}
		} catch(ValidatorException e)
		{
			throw new ServiceException(e.getMessage());
		}
		return adminResponseObj;
	}

	public Admin adminRegister(final RegisterDTO registerDTO) throws ServiceException {
		Admin adminResponseObj = null;	
		try {
			Admin adminObj = new Admin();
			adminObj.setEmail(registerDTO.getEmail());
			adminObj.setName(registerDTO.getName());
			adminObj.setPassword(registerDTO.getPassword());
			validator.registerValidator(registerDTO);
			adminResponseObj = adminRepoObj.save(adminObj);
			// Mail service
			MailDTO mailDTO = new MailDTO();
			mailDTO.setName(registerDTO.getName());
			mailDTO.setEmail(registerDTO.getEmail());
			mailService.sendMail(mailDTO);
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return adminResponseObj;
	}
	
	public Admin findById(final Integer id)
	{
		return adminRepoObj.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("ID not found"));
	}
	
	public List<Admin> listAdminDetails() throws ServiceException
	{
		List<Admin> list = null;
		list = adminRepoObj.findAll();
		if(list == null)
		{
			throw new ServiceException(MessageConstant.ADMIN_DETAILS_NOT_AVAILABLE);
		}
		return list;
	}
	
	// Forget password
		public Admin findByEmail(final String email) {
			Admin adminResponse = null;
			adminResponse = adminRepoObj.findByEmail(email);
			//Prepare message and to address
			StringBuilder sb = new StringBuilder();
			sb.append("Dear user,");
			sb.append("Your Password is,").append(adminResponse.getPassword());

			ForgotPasswordDTO mailDTO = new ForgotPasswordDTO();
			mailDTO.setTo(adminResponse.getEmail());
			mailDTO.setText(sb.toString());
			//Send mail to user
			mailService.sendMailToUser(mailDTO);
			return adminResponse;
		}
}
