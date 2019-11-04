package com.charityapp.userappms.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charityapp.userappms.client.MailClient;
import com.charityapp.userappms.dto.ForgotPasswordDTO;
import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.MailDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.dto.UserStatusDTO;
import com.charityapp.userappms.dto.UserUpdateDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.exception.ValidatorException;
import com.charityapp.userappms.model.User;
import com.charityapp.userappms.repository.UserRepository;
import com.charityapp.userappms.util.MessageConstant;
import com.charityapp.userappms.validator.UserValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepoObj;
	@Autowired
	private MailClient mailService;
	@Autowired
	private UserValidator donorValidator;

	public User donorLogin(final LoginDTO loginDTO) throws ServiceException {
		User userResponseObj = null;
		try {
			String email = loginDTO.getEmail();
			String password = loginDTO.getPassword();
			donorValidator.userLoginValidator(loginDTO);
			userResponseObj = userRepoObj.findByEmailAndPassword(email, password);
			if (userResponseObj == null) {
				throw new ServiceException(MessageConstant.INVALID_CREDENTIAL);
			}
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
		return userResponseObj;
	}

	@Transactional
	public User donorRegister(final RegisterDTO registerDTO) throws ServiceException {
		User userResponseObj = null;

		try {
			User donorObj = new User();
			donorObj.setName(registerDTO.getName());
			donorObj.setEmail(registerDTO.getEmail());
			donorObj.setPassword(registerDTO.getPassword());

			String activationCode = RandomStringUtils.randomAlphabetic(10);
//			donorObj.setName(activationCode); // todo: remove this
			donorValidator.userRegisterValidator(registerDTO);
			userResponseObj = userRepoObj.save(donorObj);
			// Mail service
			MailDTO mailDTO = new MailDTO();
			mailDTO.setName(registerDTO.getName());
			mailDTO.setEmail(registerDTO.getEmail());
			mailDTO.setCode(activationCode);
			mailService.sendMail(mailDTO);
			if (userResponseObj == null) {
				throw new ServiceException(MessageConstant.REGISTERATION_FAILED);
			}
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}

		return userResponseObj;
	}

	public User findById(Integer id) {
		return userRepoObj.findById(id).orElseThrow(() -> new EntityNotFoundException("ID not found"));
	}

	// Forget password
	public User findByEmail(final String email) {
		User donorResponse = null;
		donorResponse = userRepoObj.findByEmail(email);
		// Prepare message and to address
		StringBuilder sb = new StringBuilder();
		sb.append("Dear user,");
		sb.append("Your Password is,").append(donorResponse.getPassword());

		ForgotPasswordDTO mailDTO = new ForgotPasswordDTO();
		mailDTO.setTo(donorResponse.getEmail());
		mailDTO.setText(sb.toString());
		// Send mail to user
		mailService.sendMailToUser(mailDTO);
		return donorResponse;
	}

	public List<User> listDonorDetails() throws ServiceException {
		List<User> list = null;
		list = userRepoObj.findAll();
		if (list.isEmpty()) {
			throw new ServiceException(MessageConstant.USER_DETAILS_NOT_AVAILABLE);
		}
		return list;
	}

	@Transactional
	public void updateUserStatus(int userId, UserStatusDTO statusDTO) throws ServiceException {
		Boolean isActive = statusDTO.getActive();
		int response = userRepoObj.updateStatus(isActive, userId);
		if (response == 0) {
			throw new ServiceException(MessageConstant.UNABLE_TO_UPDATE_ACTIVE_STATUS);
		}
	}

	@Transactional
	public void updateAccountLockStatus(int userId, UserStatusDTO statusDTO) throws ServiceException {
		Boolean isActive = statusDTO.getActive();
		int responseObj = userRepoObj.updateAccountLock(isActive, userId);
		if (responseObj == 0) {
			throw new ServiceException(MessageConstant.UNABLE_TO_UPDATED_LOCK_STATUS);
		}
	}

	@Transactional
	public void updateAccountBlockStatus(int userId, UserStatusDTO statusDTO) throws ServiceException {
		Boolean isActive = statusDTO.getActive();
		int response = userRepoObj.updateAccountBlock(isActive, userId);
		if (response == 0) {
			throw new ServiceException(MessageConstant.UNABLE_TO_UPDATED_BLOCK_STATUS);
		}
	}
	
	@Transactional
	public void updateUserDetails(int id, UserUpdateDTO updateDTO) throws ServiceException
	{
		try {
			String name = updateDTO.getName();
			String email = updateDTO.getEmail();
			String password = updateDTO.getPassword();
			donorValidator.userUpdateValidator(updateDTO);
			int response = userRepoObj.updateUserDetails(name, email, password, id);
			if(response == 0)
			{
				throw new ServiceException(MessageConstant.UNABLE_TO_UPDATE_USER_DETAILS);
			}
		} catch (ValidatorException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
