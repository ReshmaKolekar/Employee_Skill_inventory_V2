package com.profile.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.dto.EmpSkillDto;
import com.profile.dto.EmployeeDto;
import com.sun.xml.bind.v2.model.core.ID;

public interface EmpSkillRepo extends JpaRepository<EmpSkillDto, ID> {

	List<EmpSkillDto> findBySkillName(String skillname);

	EmpSkillDto findBySkillNameAndEmployee(String skillname, EmployeeDto employee);

	List<EmpSkillDto> findByEmployee(EmployeeDto employee);

	EmpSkillDto findByEmployeeAndSkillid(EmployeeDto employee, int skillid);

}
