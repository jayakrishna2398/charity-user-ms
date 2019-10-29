package com.charityapp.userappms.controller;

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
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.model.Admin;
import com.charityapp.userappms.model.Donor;
import com.charityapp.userappms.service.DonorService;
import com.charityapp.userappms.util.Message;
import com.charityapp.userappms.util.MessageConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class DonorController {

	@Autowired
	DonorService donorService;

	/**
	 * User login
	 * @param email and password
	 * @return user details if login success or else invalid credential.
	 * **/
	@PostMapping("/login")
	@ApiOperation("Donor Login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = MessageConstant.LOGIN_SUCCESS, response = Donor.class),
			@ApiResponse(code = 400, message = MessageConstant.INVALID_CREDENTIAL, response = Message.class) })
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
		Donor donorResponseObj = null;
		String errorMessage = null;
		try {
			donorResponseObj = donorService.donorLogin(loginDTO);
			return new ResponseEntity<>(donorResponseObj, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * User register
	 * @param name,email, and password
	 * @return user details if register success or else invalid credential.
	 * **/
	@PostMapping("/register")
	@ApiOperation("Donor Register")
	@ApiResponses(value = { @ApiResponse(code = 200, message = MessageConstant.LOGIN_SUCCESS, response = Donor.class),
			@ApiResponse(code = 400, message = MessageConstant.INVALID_CREDENTIAL, response = Message.class) })
	public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDTO) {
		Donor donorResponseObj = null;
		String errorMessage = null;
		try {
			donorResponseObj = donorService.donorRegister(registerDTO);
			return new ResponseEntity<>(donorResponseObj, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Display user details based on id
	 * @param id
	 * @return user details if id is exist or else user details not available.
	 * **/
	@GetMapping("/{id}")
	@ApiOperation("Find By Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Donor.class),
			@ApiResponse(code = 400, message = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Message.class) })
	public ResponseEntity<Object> findById(@PathVariable int id) {
		Donor donorResponseObj = null;
		try {
			donorResponseObj = donorService.findById(id);
			return new ResponseEntity<>(donorResponseObj, HttpStatus.OK);
		} catch(EntityNotFoundException e)
		{
			Message message = new Message(MessageConstant.DONOR_DETAILS_NOT_AVAILABLE);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * forget password
	 * @param email
	 * @return user password if email is exist or else user details not available.
	 * **/
	@PostMapping("/forgetpassword")
	@ApiOperation("Forget password")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Donor.class),
			@ApiResponse(code = 400, message = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Message.class) })
	public ResponseEntity<Object> forgetPassword(@RequestParam String email) {
		Donor donorResponseObj = null;
		donorResponseObj = donorService.findByEmail(email);
		if (donorResponseObj != null) {
			return new ResponseEntity<>(donorResponseObj, HttpStatus.OK);
		} else {
			Message message = new Message(MessageConstant.DONOR_DETAILS_NOT_AVAILABLE);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * List user details
	 * @return user details until get an exception or else user details not available.
	 * **/
	@GetMapping("/list")
	@ApiOperation("List Donor Details")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = MessageConstant.DONOR_DETAILS_AVAILABLE, response = Admin.class),
			@ApiResponse(code = 400, message  = MessageConstant.DONOR_DETAILS_NOT_AVAILABLE, response = Message.class)
	})
	public ResponseEntity<Object> listDonorDetails()
	{
		List<Donor> list = null;
		String errorMessage = null;
		try {
			list = donorService.listDonorDetails();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			Message message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
}
