package com.profile.service;

import java.util.List;

import com.profile.CustomException.RecordNotFoundException;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;

public interface SkillService {

	List<SkillDto> addSkill(SkillList skillList);

	List<SkillDto> getSkillList();

	SkillDto findBySkillId(int id);

	SkillDto updateSkill(int skillId, Skills skill);

	SkillDto deleteSkill(String skillname) throws RecordNotFoundException;

}
