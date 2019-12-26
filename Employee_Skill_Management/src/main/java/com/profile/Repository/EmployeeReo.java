package com.profile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.dto.EmployeeDto;
import com.sun.xml.bind.v2.model.core.ID;

public interface EmployeeReo extends JpaRepository<EmployeeDto, ID> {

	public EmployeeDto findById(int id);

	public EmployeeDto findByEmailAndPassword(String email, String password);

	public EmployeeDto findByEmail(String email);
}
