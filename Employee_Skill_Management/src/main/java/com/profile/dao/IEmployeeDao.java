package com.profile.dao;

import com.profile.dto.EmployeeDto;

public interface IEmployeeDao {

	public boolean save(EmployeeDto emp);

	public EmployeeDto getEmpSkillsByEmpId(int id);

}
