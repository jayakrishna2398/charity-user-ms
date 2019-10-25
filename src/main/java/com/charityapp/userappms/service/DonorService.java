package com.charityapp.userappms.service;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.MailDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.Donor;
import com.charityapp.userappms.repository.DonorRepository;
import com.charityapp.userappms.util.MessageConstant;
import com.charityapp.userappms.validator.DonorValidator;

@Service
public class DonorService {

	@Autowired
	private DonorRepository donorRepoObj;
	@Autowired
	private MailService mailService;
	@Autowired
	private DonorValidator donorValidator;

	public Donor donorLogin(final LoginDTO loginDTO) throws ServiceException {
		Donor donorResponseObj = null;
		try {
			String email = loginDTO.getEmail();
			String password = loginDTO.getPassword();
			donorValidator.donorLoginValidator(loginDTO);
			donorResponseObj = donorRepoObj.findByEmailAndPassword(email,password);
			if(donorResponseObj == null)
			{
				throw new ServiceException(MessageConstant.INVALID_CREDENTIAL);
			}
		} catch(ValidatorException e)
		{
			throw new ServiceException(e.getMessage());
		}
		return donorResponseObj;
	}

	@Transactional
	public Donor donorRegister(final RegisterDTO registerDTO) {
		Donor donorResponseObj = null;
		Donor donorObj =new Donor();
		donorObj.setName(registerDTO.getName());
		donorObj.setEmail(registerDTO.getEmail());
		donorObj.setPassword(registerDTO.getPassword());
		donorResponseObj = donorRepoObj.save(donorObj);
		//Mail service
		MailDTO mailDTO = new MailDTO();
		mailDTO.setName(registerDTO.getName());
		mailDTO.setEmail(registerDTO.getEmail());
		mailService.sendMail(mailDTO);
		return donorResponseObj;
	}
	
	public Donor findById(Integer id)
	{
		return donorRepoObj.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("ID not found"));
	}
}
