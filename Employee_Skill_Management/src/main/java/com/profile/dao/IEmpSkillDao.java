package com.profile.dao;

import com.profile.dto.EmployeeDto;

public interface IEmpSkillDao {

	EmployeeDto getEmpSkillsByEmpId(int id);

}
