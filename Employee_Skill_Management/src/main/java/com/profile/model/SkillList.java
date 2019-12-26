package com.profile.model;

import java.util.List;

public class SkillList {

	List<Skills> skilllist;

	public SkillList() {
		super();
	}

	public SkillList(List<Skills> skilllist) {
		super();
		this.skilllist = skilllist;
	}

	public List<Skills> getSkilllist() {
		return skilllist;
	}

	public void setSkilllist(List<Skills> skilllist) {
		this.skilllist = skilllist;
	}

}
