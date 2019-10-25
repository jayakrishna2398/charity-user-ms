package com.charityapp.userappms.validator;

import org.springframework.stereotype.Component;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.util.MessageConstant;

@Component
public class AdminValidator {
	
	/**
	 * Get instance of donor validator class 
	**/
	/** Login validator **/
	public void loginValidator(LoginDTO loginDTO) throws ValidatorException
	{
		String email = loginDTO.getEmail();
		String password = loginDTO.getPassword();
		
		if(email == null || "".equals(email))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if(password == null || "".equals(password))
		{
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}
	}
}
