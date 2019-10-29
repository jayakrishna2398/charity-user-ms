package com.charityapp.userappms.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
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

	@PostMapping("/login")
	@ApiOperation("Admin Login")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = MessageConstant.LOGIN_SUCCESS, response = Admin.class),
			@ApiResponse(code = 400, message = MessageConstant.INVALID_CREDENTIAL, response = Message.class) 
			})
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
		Admin adminResponseObj = null;
		String errorMessage = null;
		try {
			adminResponseObj = adminServiceObj.adminLogin(loginDTO);
			return new ResponseEntity<>(adminResponseObj, HttpStatus.OK);
		} catch(ServiceException e)
		{
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/register")
	@ApiOperation("Admin Register")
	@ApiResponses(value={
			@ApiResponse(code = 200, message = MessageConstant.REGISTRATION_SUCCESS, response = Admin.class),
			@ApiResponse(code = 400, message = MessageConstant.REGISTERATION_FAILED, response = Message.class)
	})
	public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO)
	{
		Admin adminResponseObj = null;
		String errorMessage = null;
		try {
			adminResponseObj = adminServiceObj.adminRegister(registerDTO);
			return new ResponseEntity<>(adminResponseObj, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{id}")
	@ApiOperation("Find by id")
	@ApiResponses(value={
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Admin.class),
			@ApiResponse(code = 400, message = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Message.class)
	})
	public ResponseEntity<Object> findById(@PathVariable int id) {
		Admin adminResponseObj = null;
		try
		{
			adminResponseObj = adminServiceObj.findById(id);
			return new ResponseEntity<>(adminResponseObj, HttpStatus.OK);
		} catch(EntityNotFoundException e)
		{
			Message message = new Message(MessageConstant.DONOR_DETAILS_NOT_AVAILABLE);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/validate")
	@ApiOperation("Validate admin")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Boolean.class),
			@ApiResponse(code = 400, message  = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Boolean.class)
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
}
