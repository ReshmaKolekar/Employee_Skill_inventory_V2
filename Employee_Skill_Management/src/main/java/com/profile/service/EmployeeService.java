package com.profile.service;

import java.util.List;

import com.profile.CustomException.RecordNotFoundException;
import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.profile.model.Employee;
import com.profile.model.SkillList;
import com.profile.model.Skills;

public interface EmployeeService {

	String save(Employee employee);

	String sendMail(String to, boolean response);

	EmployeeDto addEmpskill(int id, SkillList skillList);

	List<EmployeeDto> getAllEmp();

	EmployeeDto getEmpById(int id);

	Employee deleteEmp(int id) throws RecordNotFoundException;

	EmployeeDto updateEmpSkill(int id, String skill_name, Skills skill);

	String authenticate(Employee employee);

	EmployeeDto updateEmpSkillSet(int id, SkillList skillList);

	EmpSkillDto deleteEmpSkillData(int id, int skillid);

	

}
