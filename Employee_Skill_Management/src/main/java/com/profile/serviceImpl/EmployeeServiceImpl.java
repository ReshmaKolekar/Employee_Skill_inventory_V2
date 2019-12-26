package com.profile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.profile.CustomException.RecordNotFoundException;
import com.profile.Repository.EmpSkillRepo;
import com.profile.Repository.EmployeeReo;
import com.profile.Repository.SkillsRepo;
import com.profile.Resource.EmployeeController;
import com.profile.dao.IEmpSkillDao;
import com.profile.dao.IEmployeeDao;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.dto.SkillDto;
import com.profile.model.Employee;
import com.profile.model.Mail;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LogManager.getLogger(EmployeeService.class);

	@Autowired
	IEmployeeDao iEmployeeDao;

	@Autowired
	IEmpSkillDao iEmpSkillDao;

	@Autowired
	EmployeeReo employeeReo;

	@Autowired
	SkillsRepo skillsRepo;

	@Autowired
	EmpSkillRepo empSkillRepo;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param employee
	 * @return object to be saved
	 * @Discription This method perform register operation for new user. If the user
	 *              is already present then it gives respective message.
	 * 
	 */
	@Override
	public String save(Employee emp) {

		logger.info("inside save employee");
		EmployeeDto employee = new EmployeeDto();
		employee.setFirstName(emp.getFirst_name());
		employee.setLastName(emp.getLast_name());
		employee.setContact(emp.getContact());
		employee.setEmail(emp.getEmail());
		employee.setPassword(encodePass(emp.getPassword()));

		EmployeeDto employeeFromDB = employeeReo.findByEmail(emp.getEmail());
		if (employeeFromDB == null) {
			boolean response = iEmployeeDao.save(employee);
			String mailResponse = sendMail(emp.getEmail(), response);
			logger.info("User is added : " + response);
			logger.info(" And Mail Response :" + mailResponse);
			return mailResponse;
		} else {
			return "User already exist";
		}
	}

	/**
	 * 
	 * @param plainPassword
	 * @return encrypted password
	 * @Discrption This method uses Bcrypt password encryption algorithm to encode
	 *             password
	 */
	public String encodePass(String plainPassword) {

		String encryptedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
		logger.info("Encrypted password : " + encryptedPassword);

		boolean matched = BCrypt.checkpw(plainPassword, encryptedPassword);
		logger.info("Is password matched: " + matched);

		return encryptedPassword;
	}

	/**
	 * @param to
	 * @param response
	 * @return mail sent or not
	 * @Discrption This method send an email of user registration
	 */
	public String sendMail(String to, boolean response) {

		String URI = "http://localhost:8085/Mail/sendmail";
		logger.info("mail URI : " + URI);
		String subject = "User Account Creation";
		String content;
		if (response == true) {
			Mail sendMail = new Mail(to, subject, "User account created Successfully");
			logger.info(" Mail Content :" + sendMail.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Mail> request = new HttpEntity<>(sendMail, headers);
			logger.info("Mail API request :" + request.toString());
			return restTemplate.postForObject(URI, request, String.class);
		} else {
			Mail sendMail = new Mail(to, subject, "User account creation failed");
			logger.info(" Mail Content :" + sendMail.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Mail> request = new HttpEntity<>(sendMail, headers);
			logger.info("Mail API request :" + request.toString());
			return restTemplate.postForObject(URI, request, String.class);
		}
	}

	/**
	 * @param employee
	 * @return String of user authentication result
	 * @Discription This method returns success for valid credentials otherwise
	 *              returns respective error msg
	 */
	@Override
	public String authenticate(Employee employee) {
		EmployeeDto employeeFromDb = employeeReo.findByEmail(employee.getEmail());

		String authenticationResponse;
		if (employeeFromDb != null) {
			if (BCrypt.checkpw(employee.getPassword(), employeeFromDb.getPassword())) {
				authenticationResponse = "User is authenticated Successfully !!!";
				logger.info(authenticationResponse);

			} else
				authenticationResponse = " incorrect password ";
			logger.info(authenticationResponse);

		} else {
			authenticationResponse = "You have entered incorrect email_id";
			logger.info(authenticationResponse);

		}
		return authenticationResponse;
	}

	/**
	 * @param id
	 * @param skillList
	 * @Discription This method persist the skills for user
	 */
	@Override
	public EmployeeDto addEmpskill(int empId, SkillList skillList) {

		logger.info(" inside addEmpskill");
		logger.info("Employee id :" + empId + " list of skills :" + skillList.getSkilllist());
		List<EmpSkillDto> empSkillDtoList = new ArrayList<EmpSkillDto>();
		for (Skills skill : skillList.getSkilllist()) {
			SkillDto skillDto = skillsRepo.findBySkillName(skill.getSkillName());
			empSkillDtoList.add(new EmpSkillDto(skillDto.getSkillid(), skillDto.getSkillName(), skill.getExperiance()));
		}

		logger.info("Get an employee object of pathvariable id to inject into empskill object");
		EmployeeDto employee = employeeReo.findById(empId);
		if (employee != null) {
			for (EmpSkillDto empSkill : empSkillDtoList) {
				empSkill.setEmployee(employee);
				logger.info("Employee-Skill mapping to be added :" + empSkill.toString());
				empSkillRepo.save(empSkill);
			}
		}
		return employeeReo.findById(empId);
	}

	/**
	 * @return list of all employee details
	 */
	@Override
	public List<EmployeeDto> getAllEmp() {

		return employeeReo.findAll();
	}

	/**
	 * @param empId
	 * @return employee details of specified Employee ID
	 */
	@Override
	public EmployeeDto getEmpById(int empId) {
		logger.info("inside getEmpById ");
		EmployeeDto employee = employeeReo.findById(empId);
		logger.info("Employee details for empID:" + empId + "\n" + employee.toString());
		return employee;

	}

	/**
	 * @param empid
	 * @return deleted employee object
	 * @throws RecordNotFoundException
	 * @Discription delete the record
	 */
	@Override
	public Employee deleteEmp(int empid) throws RecordNotFoundException {

		EmployeeDto empToBeDeleted = employeeReo.findById(empid);
		logger.info("employee to be deleted : " + empToBeDeleted);
		if (empToBeDeleted == null)
			throw new RecordNotFoundException("Record not found with employee_id " + empid);

		Employee deletedEmployee = new Employee(empToBeDeleted.getContact(), empToBeDeleted.getFirstName(),
				empToBeDeleted.getLastName(), empToBeDeleted.getEmail(), empToBeDeleted.getPassword());

		employeeReo.delete(empToBeDeleted);
		return deletedEmployee;
	}

	/**
	 * @param id
	 * @param skill_name
	 * @param skill
	 * @return updated employee info
	 * @Discription This method perform an update action for single employee skill
	 *              mapping if the data is present for specified Id else it add new
	 *              resource
	 */
	@SuppressWarnings("unused")
	@Override
	public EmployeeDto updateEmpSkill(int id, String skillName, Skills skill) {

		EmployeeDto employee = employeeReo.findById(id);
		logger.info(" employee record to update : " + employee.toString());
		try {
			EmpSkillDto empSkillToBeUpdated = empSkillRepo.findBySkillNameAndEmployee(skillName, employee);
			logger.info(" employee - skill mapping to be updated : " + empSkillToBeUpdated.toString());
			if (empSkillToBeUpdated != null) {
				logger.info("update existing employee - skill mapping");
				empSkillToBeUpdated.setExperiance(skill.getExperiance());

				empSkillRepo.save(empSkillToBeUpdated);
				logger.info("Employee-skill mapping to be updated :" + empSkillToBeUpdated);
			} else {
				logger.info("create new employee - skill mapping");
				SkillDto skillDto = skillsRepo.findBySkillName(skillName);
				EmpSkillDto newEmpSkillDto = new EmpSkillDto();
				newEmpSkillDto.setSkillid(skillDto.getSkillid());
				newEmpSkillDto.setSkillName(skillDto.getSkillName());
				newEmpSkillDto.setExperiance(skill.getExperiance());
				newEmpSkillDto.setEmployee(employee);

				empSkillRepo.save(newEmpSkillDto);
				logger.info("Employee-skill mapping to be added :" + newEmpSkillDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeReo.findById(id);
	}

	/**
	 * 
	 * @param empid
	 * @param skillList
	 * @return updated employee skill details
	 * @Discription This method perform an update action for employee - skills
	 *              mapping if the data is present for specified Id else it create
	 *              new resource
	 */
	@SuppressWarnings("unused")
	@Override
	public EmployeeDto updateEmpSkillSet(int id, SkillList skillList) {
		EmployeeDto employee = employeeReo.findById(id);
		logger.info(" employee record to update : " + employee.toString());
		try {
			for (Skills skillToBeupdatedWith : skillList.getSkilllist()) {
				EmpSkillDto empSkillToBeUpdated = empSkillRepo
						.findBySkillNameAndEmployee(skillToBeupdatedWith.getSkillName(), employee);
				logger.info(" employee - skill mapping to be updated : " + empSkillToBeUpdated.toString());
				if (empSkillToBeUpdated != null) {
					empSkillToBeUpdated.setExperiance(skillToBeupdatedWith.getExperiance());
					empSkillRepo.save(empSkillToBeUpdated);
					logger.info("Employee-skill mapping to be updated :" + empSkillToBeUpdated);
				} else {
					logger.info("create new employee - skill mapping");
					SkillDto skillDto = skillsRepo.findBySkillName(skillToBeupdatedWith.getSkillName());
					EmpSkillDto newEmpSkillDto = new EmpSkillDto();
					newEmpSkillDto.setSkillid(skillDto.getSkillid());
					newEmpSkillDto.setSkillName(skillDto.getSkillName());
					newEmpSkillDto.setExperiance(skillToBeupdatedWith.getExperiance());
					newEmpSkillDto.setEmployee(employee);

					empSkillRepo.save(newEmpSkillDto);
					logger.info("Employee-skill mapping to be added :" + newEmpSkillDto);
				}
			}
		} catch (Exception e) {
			logger.info(e.toString());
		}

		logger.info(" Updated Employee-skill mapping :" + employeeReo.findById(id).toString());
		return employeeReo.findById(id);
	}

	/**
	 * 
	 * @param empid
	 * @param skillid
	 * @return deleted employee skill details
	 * @Discription This method deletes the specified employee - skill mapping
	 * 
	 */
	@Override
	public EmpSkillDto deleteEmpSkillData(int empid, int skillid) {

		logger.info("inside deleteEmpSkillData");
		EmployeeDto employee = employeeReo.findById(empid);
		logger.info("Employee Object:" +employee.toString());
		EmpSkillDto empSkillDtoToBeDeleted = empSkillRepo.findByEmployeeAndSkillid(employee, skillid);
		empSkillRepo.delete(empSkillDtoToBeDeleted);
		logger.info("Employee-skill mapping to be deleted :" + empSkillDtoToBeDeleted); 
		return empSkillDtoToBeDeleted;
	}
}
