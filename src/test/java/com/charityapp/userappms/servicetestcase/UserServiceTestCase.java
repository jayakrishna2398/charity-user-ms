package com.charityapp.userappms.servicetestcase;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.dto.UserUpdateDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.model.User;
import com.charityapp.userappms.service.UserService;

@SpringBootTest
public class UserServiceTestCase {
	@Autowired
	UserService userService;
	@Test
	public void testUserRegister()
	{
		User userResponseObj = null;
		try {
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setName("krishna");
			registerDTO.setEmail("krishna192168@gmail.com");
			registerDTO.setPassword("krishna");
			userResponseObj = userService.donorRegister(registerDTO);
			assertNotNull(userResponseObj);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void testUserLogin()
	{
		LoginDTO loginDTO = new LoginDTO();
		User userResponseObj = null;
		try {
			loginDTO.setEmail("keyne.loui@gmail.com");
			loginDTO.setPassword("keyne");
			userResponseObj = userService.donorLogin(loginDTO);
			assertNotNull(userResponseObj);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void testFindById()
	{
		User userResponseObj = null;
		userResponseObj = userService.findById(1);
		assertNotNull(userResponseObj);
	}
	@Test
	public void testFindByEmail() 
	{
		User userResponseObj = null;
		userResponseObj = userService.findByEmail("keyne.loui@gmail.com");
		assertNotNull(userResponseObj);
	}
	@Test
	public void testListDonorDetails()
	{
		List<User> list = null;
		try {
			list = userService.listDonorDetails();
			assertNotNull(list);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void testUpdateUserDetails()
	{
		
		try {
			UserUpdateDTO updateDTO = new UserUpdateDTO();
			updateDTO.setName("keyne loui");
			updateDTO.setEmail("keyne.loui@gmail.com");
			updateDTO.setPassword("keyne");
			userService.updateUserDetails(1, updateDTO);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
}
