package com.charityapp.userappms.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charityapp.userappms.client.MailClient;
import com.charityapp.userappms.dto.ForgotPasswordDTO;
import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.MailDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.dto.UserUpdateDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.Employee;
import com.charityapp.userappms.repository.EmployeeRepository;
import com.charityapp.userappms.util.MessageConstant;
import com.charityapp.userappms.validator.EmployeeValidator;
import com.charityapp.userappms.validator.UserValidator;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepoObj;
	@Autowired
	EmployeeValidator validator;
	@Autowired
	UserValidator donorValidator;
	@Autowired
	private MailClient mailService;

	public Employee employeeLogin(final LoginDTO loginDTO) throws ServiceException {
		Employee adminResponseObj = null;
		try {
			String email = loginDTO.getEmail();
			String password = loginDTO.getPassword();
			validator.loginValidator(loginDTO);
			adminResponseObj = employeeRepoObj.findByEmailAndPassword(email, password);
			if (adminResponseObj == null) {
				throw new ServiceException(MessageConstant.INVALID_CREDENTIAL);
			}
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return adminResponseObj;
	}

	public Employee employeeRegister(final RegisterDTO registerDTO) throws ServiceException {
		Employee adminResponseObj = null;
		try {
			Employee adminObj = new Employee();
			adminObj.setEmail(registerDTO.getEmail());
			adminObj.setName(registerDTO.getName());
			adminObj.setPassword(registerDTO.getPassword());
			validator.registerValidator(registerDTO);
			adminResponseObj = employeeRepoObj.save(adminObj);
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

	public Employee findById(final Integer id) {
		return employeeRepoObj.findById(id).orElseThrow(() -> new EntityNotFoundException("ID not found"));
	}

	public List<Employee> listAdminDetails() throws ServiceException {
		List<Employee> list = null;
		list = employeeRepoObj.findAll();
		if (list.isEmpty()) {
			throw new ServiceException(MessageConstant.EMPLOYEE_DETAILS_NOT_AVAILABLE);
		}
		return list;
	}

	// Forget password
	public Employee findByEmail(final String email) {
		Employee adminResponse = null;
		adminResponse = employeeRepoObj.findByEmail(email);
		// Prepare message and to address
		StringBuilder sb = new StringBuilder();
		sb.append("Dear user,");
		sb.append("Your Password is,").append(adminResponse.getPassword());

		ForgotPasswordDTO mailDTO = new ForgotPasswordDTO();
		mailDTO.setTo(adminResponse.getEmail());
		mailDTO.setText(sb.toString());
		// Send mail to user
		mailService.sendMailToUser(mailDTO);
		return adminResponse;
	}
	
	@Transactional
	public void updateEmployeeDetails(int id, UserUpdateDTO updateDTO) throws ServiceException
	{
		try {
			String name = updateDTO.getName();
			String email = updateDTO.getEmail();
			String password = updateDTO.getPassword();
			donorValidator.userUpdateValidator(updateDTO);
			int response = employeeRepoObj.updateEmployeeDetails(name, email, password, id);
			if(response == 0)
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_UPDATE_USER_DETAILS);
			}
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
