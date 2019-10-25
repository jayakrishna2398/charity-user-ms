package com.charityapp.userappms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charityapp.userappms.dto.LoginDTO;
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
	@Autowired AdminValidator validator;

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

	public Admin adminRegister(final Admin adminObj) {
		Admin adminResponseObj = null;
		adminResponseObj = adminRepoObj.save(adminObj);
		return adminResponseObj;
	}
}
