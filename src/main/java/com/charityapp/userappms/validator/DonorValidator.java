package com.charityapp.userappms.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.Donor;
import com.charityapp.userappms.repository.DonorRepository;
import com.charityapp.userappms.util.MessageConstant;



@Component
public class DonorValidator {
	
	@Autowired
	private DonorRepository donorRepo;
	
	
	public void donorLoginValidator(LoginDTO loginDTO) throws ValidatorException
	{	
		
		if(StringUtils.isEmpty(loginDTO.getEmail()))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		
		
		if(StringUtils.isEmpty(loginDTO.getPassword()))
		{
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}
	}
	
	public void donorRegisterValidator(Donor donor) throws ValidatorException
	{
		String email = donor.getEmail();
		String password = donor.getPassword();
		
		
		
		if(StringUtils.isEmpty(email))
		{
			throw new ValidatorException(MessageConstant.INVALID_EMAIL);
		}
		if(StringUtils.isEmpty(password))
		{
			throw new ValidatorException(MessageConstant.INVALID_PASSWORD);
		}
		
		//Prepare get donor details based on email
		Donor donorResponseObj = donorRepo.findByEmail(email);
		
		if(donorResponseObj != null)
		{
			throw new ValidatorException(MessageConstant.EMAIL_EXIST);
		}
		
		
	}

}
