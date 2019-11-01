package com.charityapp.userappms.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.User;
import com.charityapp.userappms.repository.UserRepository;
import com.charityapp.userappms.util.MessageConstant;



@Component
public class UserValidator {
	
	@Autowired
	private UserRepository userRepo;
	
	
	public void userLoginValidator(LoginDTO loginDTO) throws ValidatorException
	{	
		
		if(StringUtils.isEmpty(loginDTO.getEmail()))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL_OR_PASSWORD);
		}
		
		
		if(StringUtils.isEmpty(loginDTO.getPassword()))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL_OR_PASSWORD);
		}
	}
	
	public void userRegisterValidator(RegisterDTO registerDTO) throws ValidatorException
	{
		String email = registerDTO.getEmail();
		String password = registerDTO.getPassword();
		String name = registerDTO.getName();
		
		
		if(StringUtils.isEmpty(name))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if(StringUtils.isEmpty(email))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if(StringUtils.isEmpty(password))
		{
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}
		
		//Prepare get donor details based on email
		User userResponseObj = userRepo.findByEmail(email);
		
		if(userResponseObj != null)
		{
			throw new ValidatorException(MessageConstant.EMAIL_EXIST);
		}
		
		
	}

}
