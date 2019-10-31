package com.charityapp.userappms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.dto.UserDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.model.Admin;
import com.charityapp.userappms.service.AdminService;
import com.charityapp.userappms.util.Message;
import com.charityapp.userappms.util.MessageConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminServiceObj;

	/**
	 * Admin login
	 * @param email and password
	 * @return admin details if login success or else invalid credential.
	 * **/
	@PostMapping("/login")
	@ApiOperation("Admin Login")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = MessageConstant.LOGIN_SUCCESS, response = UserDTO.class),
			@ApiResponse(code = 400, message = MessageConstant.INVALID_CREDENTIAL, response = Message.class) 
			})
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
		Admin adminResponseObj = null;
		String errorMessage = null;
		try {
			adminResponseObj = adminServiceObj.adminLogin(loginDTO);
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(adminResponseObj.getEmail());
			userDTO.setName(adminResponseObj.getName());
			userDTO.setId(adminResponseObj.getId());
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch(ServiceException e)
		{
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}
	/**
	 * Admin register
	 * @param name,email, and password
	 * @return admin details if register success or else invalid credential.
	 * **/
	@PostMapping("/register")
	@ApiOperation("Admin Register")
	@ApiResponses(value={
			@ApiResponse(code = 200, message = MessageConstant.REGISTRATION_SUCCESS, response = UserDTO.class),
			@ApiResponse(code = 400, message = MessageConstant.REGISTERATION_FAILED, response = Message.class)
	})
	public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO)
	{
		Admin adminResponseObj = null;
		String errorMessage = null;
		try {
			adminResponseObj = adminServiceObj.adminRegister(registerDTO);
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(adminResponseObj.getEmail());
			userDTO.setName(adminResponseObj.getName());
			userDTO.setId(adminResponseObj.getId());
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * Display admin details based on id
	 * @param id
	 * @return admin details if id is exist or else admin details not available.
	 * **/
	@GetMapping("/{id}")
	@ApiOperation("Find By Id")
	@ApiResponses(value={
			@ApiResponse(code = 200, message = MessageConstant.ADMIN_DETAILS_AVAILABLE, response = UserDTO.class),
			@ApiResponse(code = 400, message = MessageConstant.ADMIN_DETAILS_NOT_AVAILABLE, response = Message.class)
	})
	public ResponseEntity<Object> findById(@PathVariable int id) {
		Admin adminResponseObj = null;
		try
		{
			adminResponseObj = adminServiceObj.findById(id);
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail(adminResponseObj.getEmail());
			userDTO.setName(adminResponseObj.getName());
			userDTO.setId(adminResponseObj.getId());
			return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch(EntityNotFoundException e)
		{
			Message message = new Message(MessageConstant.DONOR_DETAILS_NOT_AVAILABLE);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Validate admin based on id
	 * @param id
	 * @return admin details if id is exist or else admin details not available.
	 * **/
	@GetMapping("/{id}/validate")
	@ApiOperation("Validate Admin")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = MessageConstant.ADMIN_DETAILS_AVAILABLE, response = Boolean.class),
			@ApiResponse(code = 400, message  = MessageConstant.ADMIN_DETAILS_NOT_AVAILABLE, response = Boolean.class)
	})
	public Boolean validateAdmin(@PathVariable int id)
	{
		Boolean isValid = true;
		try {
			adminServiceObj.findById(id);
		}
		catch(EntityNotFoundException e)
		{
			isValid = false;
		}
		return isValid;
	}
	/**
	 * List admin details
	 * @return admin details until get an exception or else admin details not available.
	 * **/
	@GetMapping("/list")
	@ApiOperation("List Admin Details")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = MessageConstant.ADMIN_DETAILS_AVAILABLE, response = UserDTO.class),
			@ApiResponse(code = 400, message  = MessageConstant.ADMIN_DETAILS_NOT_AVAILABLE, response = Message.class)
	})
	public ResponseEntity<Object> listAdminDetails()
	{
		List<Admin> list = null;
		List<UserDTO> listDTO = new ArrayList<UserDTO>();
		String errorMessage = null;
		try {
			list = adminServiceObj.listAdminDetails();
			for(Admin admin : list)
			{
				UserDTO userDTO = new UserDTO();
				userDTO.setEmail(admin.getEmail());
				userDTO.setName(admin.getName());
				userDTO.setId(admin.getId());
				listDTO.add(userDTO);
			}
			return new ResponseEntity<>(listDTO, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 * forgot password
	 * @param email
	 * @return user password if email is exist or else user details not available.
	 * **/
	@GetMapping("/forgotpassword")
	@ApiOperation("Forgot password")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Message.class),
			@ApiResponse(code = 400, message = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Message.class) })
	public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
		Admin adminResponseObj = null;
		adminResponseObj = adminServiceObj.findByEmail(email);
		if (adminResponseObj != null) {
			Message message = new Message(MessageConstant.MAIL_HAS_BEEN_SEND);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			Message message = new Message(MessageConstant.MAIL_HAS_BEEN_NOT_SEND);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
}
