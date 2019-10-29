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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.model.Donor;
import com.charityapp.userappms.service.DonorService;
import com.charityapp.userappms.util.Message;
import com.charityapp.userappms.util.MessageConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/donor")
public class DonorController {

	@Autowired
	DonorService donorService;

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
}
