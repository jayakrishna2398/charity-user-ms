package com.charityapp.userappms.servicetestcase;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.charityapp.userappms.dto.LoginDTO;
import com.charityapp.userappms.dto.RegisterDTO;
import com.charityapp.userappms.exception.ServiceException;
import com.charityapp.userappms.model.Employee;
import com.charityapp.userappms.service.EmployeeService;
@SpringBootTest
public class EmployeeServiceTestCase {
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void testEmployeeRegister()
	{
		Employee employeeResponseObj = null;
		try {
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setName("krishna");
			registerDTO.setEmail("krishna192168@gmail.com");
			registerDTO.setPassword("krishna");
			employeeResponseObj = employeeService.employeeRegister(registerDTO);
			assertNotNull(employeeResponseObj);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testEmployeeLogin()
	{
		Employee employeeResponseObj = null;	
		try {
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setEmail("keyne.loui@gmail.com");
			loginDTO.setPassword("keyne");
			employeeResponseObj = employeeService.employeeLogin(loginDTO);
			assertNotNull(employeeResponseObj);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testFindById()
	{
		Employee employeeResponseObj = null;
		employeeResponseObj = employeeService.findById(1);
		assertNotNull(employeeResponseObj);
	}
	
	@Test
	public void testListAdminDetails()
	{
		List<Employee> list = null; 
		try {
			list = employeeService.listAdminDetails();
			assertNotNull(list);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testFindByEmail()
	{
		Employee employeeResponseObj = null;
		employeeResponseObj = employeeService.findByEmail("keyne.loui@gmail.com");
		assertNotNull(employeeResponseObj);
	}
}
