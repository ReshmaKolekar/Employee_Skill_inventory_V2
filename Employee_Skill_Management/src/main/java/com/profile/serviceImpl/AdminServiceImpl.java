package com.profile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.Repository.EmpSkillRepo;
import com.profile.Repository.EmployeeReo;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	EmpSkillRepo empSkillRepo;

	@Autowired
	EmployeeReo employeeReo;

	/**
	 * 
	 * @param skillname
	 * @return employee details
	 * @Discription Returns all employee details with specified skill
	 */
	@Override
	public List<EmployeeDto> getAllEmpBySkillName(String skillname) {

		List<EmpSkillDto> empSkillDtoList = empSkillRepo.findBySkillName(skillname);
		List<EmployeeDto> employeeList = new ArrayList<EmployeeDto>();
		for (EmpSkillDto employeeSkillObject : empSkillDtoList) {
			EmployeeDto employee = employeeReo.findById(employeeSkillObject.getEmployee().getId());
			employeeList.add(employee);
		}
		return employeeList;
	}
}
