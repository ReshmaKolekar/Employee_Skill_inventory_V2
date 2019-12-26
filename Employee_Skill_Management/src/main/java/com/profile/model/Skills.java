package com.profile.model;

public class Skills {

	private int skillid;
	float experiance;
	private String skillName;

	public Skills() {
		super();
	}

	public Skills(int skillid, String skillName, float experiance) {
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

	public float getExperiance() {
		return experiance;
	}

	public void setExperiance(float experiance) {
		this.experiance = experiance;
	}

	/*
	 * public Skills(int id, String skillName) { super(); this.id = id;
	 * this.skillName = skillName; }
	 */

}
