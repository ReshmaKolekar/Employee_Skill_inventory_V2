package com.profile.Resource;

import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.profile.CustomException.InternalServerError;
import com.profile.CustomException.RecordNotFoundException;
import com.profile.Repository.EmpSkillRepo;
import com.profile.Repository.EmployeeReo;
import com.profile.Repository.SkillsRepo;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.model.Employee;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Reshma
 * @Desciption This Controller contain Register, login and Employee and
 *             Employee-Skill mapping API's
 *
 */
@RestController
@RequestMapping("/rest/Employee")
@Api(value = "Employee Resource ", description = "Employee data Management")
public class EmployeeController {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	EmployeeReo employeeReo;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	SkillsRepo skillsRepo;

	@Autowired
	EmpSkillRepo empSkillRepo;

	/**
	 * @param employee
	 * @return object to be saved
	 * @Discription This method validate the data and register the valid user by
	 *              saving data in DB
	 */
	@ApiOperation(value = "Register the user ")
	@PostMapping("/createemployee")
	public EmployeeDto createUser(@Valid @RequestBody EmployeeDto employee) {

		// return employeeService.save(employee);
		return employeeReo.save(employee);
	}

	/**
	 * @param employee
	 * @return authentication result
	 * @Discription This method perform authentication of user by comparing input
	 *              credentials with data saved at registration
	 */
	@ApiOperation(value = "Authenticate the user ")
	@PostMapping("/login")
	public String authenticate(@RequestBody Employee employee) {

		return employeeService.authenticate(employee);
	}

	/**
	 * @return list of employees details
	 * @Discription this method returns all employee details
	 */
	@ApiOperation(value = "Returns List of employees with details ")
	@GetMapping("/EmployeeList")
	public List<EmployeeDto> getAllEmp() {

		return employeeService.getAllEmp();
	}

	/**
	 * 
	 * @param id
	 * @param skillList
	 * @return Employee object to be saved
	 * @Discription This method saves skills of employee
	 */
	@ApiOperation(value = "Save user skills ")
	@PostMapping("/addemployeeskill/{id}")
	public ResponseEntity<EmployeeDto> addEmpskill(@PathVariable(value = "id") int id,
			@RequestBody SkillList skillList) {

		EmployeeDto employee = employeeService.addEmpskill(id, skillList);
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Delete Employee data ")
	@DeleteMapping("/delete/employee/{id}")
	public ResponseEntity<?> deleteEmplSkills(@PathVariable(value = "id") int empid) throws RecordNotFoundException {

		Employee deletedEmployee = employeeService.deleteEmp(empid);
		return ResponseEntity.ok().body(deletedEmployee + " \n" + " is deleted");
	}

	/**
	 * 
	 * @param id
	 * @return employee object
	 * @throws RecordNotFoundException
	 * @Discription This method returns the specific employee details
	 */
	@ApiOperation(value = "Returns employee data by id ")
	@GetMapping("/getEmp/{id}")
	public ResponseEntity<?> getEmpById(@PathVariable(value = "id") int id) throws RecordNotFoundException {

		EmployeeDto employeeDto = employeeService.getEmpById(id);
			if (employeeDto == null)
				throw new RecordNotFoundException("Record not found with employee_id " + id);
		return ResponseEntity.ok().body(employeeDto);
	}

	/**
	 * @param id
	 * @param skill_name
	 * @param skill
	 * @return updated employee info
	 * @throws InternalServerError
	 * @Discription This method perform an update action for single employee skill
	 *              mapping if the data is present for specified Id else it add new
	 *              resource
	 */
	@ApiOperation(value = "Update one skill ")
	@PutMapping("/update/empskill/{empId}/{skillName}")
	public ResponseEntity<?> updateEmpSkill(@PathVariable(value = "empId") int empid,
			@PathVariable(value = "skillName") String skillName, @RequestBody Skills skill) throws InternalServerError {

		EmployeeDto employee = employeeService.updateEmpSkill(empid, skillName, skill);
			if (employee == null)
				throw new InternalServerError(" Failure while updating skill " + empid);
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * 
	 * @param empid
	 * @param skillList
	 * @return updated employee skill details
	 * @throws InternalServerError 
	 * @Discription This method perform an update action for employee - skills
	 *              mapping if the data is present for specified Id else it create
	 *              new resource
	 */
	@ApiOperation(value = "Update multiple skills ")
	@PutMapping("/update/empskills/{empid}")
	public ResponseEntity<?> updateEmpSkills(@PathVariable(value = "id") int empid, @RequestBody SkillList skillList) throws InternalServerError {

		EmployeeDto employee = employeeService.updateEmpSkillSet(empid, skillList);
			if (employee == null)
				throw new InternalServerError(" Failure while updating skill " + empid);
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * 
	 * @param empid
	 * @param skillid
	 * @return deleted employee skill details
	 * @throws RecordNotFoundException 
	 * @Discription This method deletes the specified employee - skill mapping
	 * 
	 */
	@ApiOperation(value = "Delete Employee Skill mapping data ")
	@DeleteMapping("/delete/empSkill/{empid}/{skillid}")
	public ResponseEntity<?> deleteEmpSkillData(@PathVariable(value = "empid") int empid,
			@PathVariable(value = "skillid") int skillid) throws RecordNotFoundException {

		EmpSkillDto response = employeeService.deleteEmpSkillData(empid, skillid);
			if (response == null)
				throw new RecordNotFoundException("Record not found with employee_id " + empid);
		return ResponseEntity.ok()
				.body("Mapping for employee: " + empid + " with Skillid : " + skillid + " is deleted");
	}

}
