package com.profile.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Emp_Skill")
public class EmpSkillDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "skillId")
	private int skillid;

	@Column(name = "skillName")
	private String skillName;

	//@NotBlank
	@Column(name = "exp_in_year")
	private float experiance;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private EmployeeDto employee;

	public EmpSkillDto() {
		super();
	}

	public EmpSkillDto(int skillid, String skillName, @NotNull float experiance) {
		super();
		this.skillid = skillid;
		this.skillName = skillName;;;
		this.experiance = experiance;
	}

	public int getSkillid() {
		return skillid;
	}

	public void setSkillid(int skillid) {
		this.skillid = skillid;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public float getExperiance() {
		return experiance;
	}

	public void setExperiance(float experiance) {
		this.experiance = experiance;
	}

	public EmployeeDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "EmpSkillDto [skillid=" + skillid + ", skillName=" + skillName + ", experiance=" + experiance
				+ ", employee=" + employee + "]";
	}

}
