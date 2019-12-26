package com.profile.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.profile.Repository.EmployeeReo;
import com.profile.dto.EmployeeDto;

@Repository
public class EmpSkillDaoImpl implements IEmpSkillDao {

	@Autowired
	EmployeeReo employeeReo;

	@Override
	public EmployeeDto getEmpSkillsByEmpId(int id) {

		return employeeReo.findById(id);
	}

}
