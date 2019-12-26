package com.profile.service;

import java.util.List;

import com.profile.dto.EmployeeDto;

public interface AdminService {

	List<EmployeeDto> getAllEmpBySkillName(String skillname);

}
