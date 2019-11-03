package com.charityapp.userappms.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.Employee;
import com.charityapp.userappms.repository.EmployeeRepository;
import com.charityapp.userappms.util.MessageConstant;

@Component
public class EmployeeValidator {

	@Autowired
	private EmployeeRepository adminRepo;

	/**
	 * Get instance of donor validator class
	 **/
	/** Login validator **/
	public void loginValidator(LoginDTO loginDTO) throws ValidatorException {
		String email = loginDTO.getEmail();
		String password = loginDTO.getPassword();

		if (email == null || "".equals(email)) {
			throw new ValidatorException(MessageConstant.INVALID_EMAIL_OR_PASSWORD);
		}
		if (password == null || "".equals(password)) {
			throw new ValidatorException(MessageConstant.INVALID_EMAIL_OR_PASSWORD);
		}
	}

	public void registerValidator(RegisterDTO registerDTO) throws ValidatorException {
		String name = registerDTO.getName();
		String email = registerDTO.getEmail();
		String password = registerDTO.getPassword();

		if (name == null || "".equals(name)) {
			throw new ValidatorException(MessageConstant.INVALID_USERNAME);
		}
		if (email == null || "".equals(email)) {
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if (password == null || "".equals(password)) {
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}

		// Prepare get donor details based on email
		Employee adminResponseObj = adminRepo.findByEmail(email);

		if (adminResponseObj != null) {
			throw new ValidatorException(MessageConstant.EMAIL_EXIST);
		}
	}
}
