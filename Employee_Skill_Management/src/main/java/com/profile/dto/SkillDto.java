package com.profile.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Skills")
public class SkillDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "skillid")
	private int skillid;

	@NotBlank
	@Column(name = "name")
	private String skillName;

	public SkillDto() {
		super();
	}

	public SkillDto(int skillid, String skillName) {
		super();
		this.skillid = skillid;
		this.skillName = skillName;
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

}
