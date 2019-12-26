package com.profile.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.profile.dto.EmployeeDto;
import com.profile.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Reshma
 * @Discription This Controller contains Admin API's like basic search 
 */
@RestController
@RequestMapping("/rest/admin")
@Api(value = "Admin Resource ", description = "Admin functions")
public class AdminController {

	@Autowired
	AdminService adminService;

	/**
	 * 
	 * @param skillname
	 * @return employee details
	 * @Discription Returns all employee details with specified skill
	 */
	@ApiOperation(value = "Returns all employees with specified skill ")
	@GetMapping("/basicSearch/{skillname}")
	public List<EmployeeDto> getAllEmp(@PathVariable(value = "skillname") String skillname) {
		
		return adminService.getAllEmpBySkillName(skillname);
	}
}
