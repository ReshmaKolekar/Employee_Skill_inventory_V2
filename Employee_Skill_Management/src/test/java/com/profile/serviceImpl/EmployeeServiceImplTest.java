package com.profile.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Assert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.profile.Repository.EmployeeReo;
import com.profile.dao.IEmployeeDao;
//import com.profile.dao.EmployeeDao;
import com.profile.dto.EmployeeDto;
import com.profile.model.Employee;

class EmployeeServiceImplTest {

	@InjectMocks
	EmployeeServiceImpl employeeservice;

	@Mock
	EmployeeReo employeeReo;
	IEmployeeDao iEmployeeDao;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	public EmployeeDto getEmployeeFortest() {
		EmployeeDto employee = new EmployeeDto();

		employee.setId(1);
		employee.setContact(99604765);
		employee.setFirstName("Reshma");
		employee.setLastName("Walekar");
		employee.setEmail("reshmawalekar2694@gmail.com");
		employee.setPassword("reshma");
		return employee;
	}
	
	@Disabled
	@Test
	void save() {

		Employee emp = new Employee();
		emp.setContact(996047219);
		emp.setEmail("reshmawalekar123@gmail.com");
		emp.setFirst_name("Reshma");
		emp.setLast_name("Walekar");
		emp.setPassword("reshma");

		EmployeeDto employee = new EmployeeDto();
		// employee.setId(1);
		employee.setFirstName("Reshma");
		employee.setLastName("Walekar");
		employee.setContact(996047219);
		employee.setEmail("reshmawalekar123@gmail.com");
		employee.setPassword(encodePass("Reshma"));

		// Assert.assertEquals(0, employeeReo.count());
		// Assert.assertEquals(0, employeeReo.count());

		when(employeeReo.findByEmail(anyString())).thenReturn(employee);
		String response = employeeservice.save(emp);

		Assert.assertEquals(1, employeeReo.count());

	}

	public String encodePass(String plainPassword) {

		String encryptedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
		System.out.println(encryptedPassword);

		boolean matched = BCrypt.checkpw(plainPassword, encryptedPassword);
		System.out.println(matched);

		return encryptedPassword;
	}

	
	@Test
	void getEmpById() {
		// List<EmpSkillDto> empSkillDtoList = new ArrayList<EmpSkillDto>();

		when(employeeReo.findById(anyInt())).thenReturn(getEmployeeFortest());
		EmployeeDto employeeObject = employeeservice.getEmpById(1);

		assertNotNull(employeeObject);
		assertEquals("Reshma", employeeObject.getFirstName());
		assertEquals("Walekar", employeeObject.getLastName());

	}

	
	@Disabled
	@Test
	public void deleteEmp() {

		EmployeeDto employee = new EmployeeDto();

		employee.setId(1);
		employee.setContact(99604765);
		employee.setFirstName("Reshma");
		employee.setLastName("Walekar");
		employee.setEmail("reshmawalekar2694@gmail.com");
		employee.setPassword("reshma");
		
		employeeReo.delete(employee);
		verify(employeeReo, times(1)).delete(getEmployeeFortest());
	}

	
	
//	@Test
//	void testNullPointerExceptionForGetEmployeebyID() {
//
//		when(employeeReo.findById(anyInt())).thenReturn(null);
//
//		Assertions.assertThrows(NullPointerException.class, () -> {
//			employeeservice.getEmpByID(1);
//		});
//
//	}

}
