package com.profile.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.profile.CustomException.RecordNotFoundException;
import com.profile.Repository.SkillsRepo;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	SkillsRepo skillsRepo;

	@Override
	public List<SkillDto> addSkill(SkillList skillList) {

		List<SkillDto> skillDtolist = new ArrayList<SkillDto>();

		for (Skills skill : skillList.getSkilllist()) {
			SkillDto skillDto = new SkillDto();
			skillDto.setSkillName(skill.getSkillName());
			skillDtolist.add(skillDto);
		}
		return skillsRepo.saveAll(skillDtolist);
	}

	@Override
	public List<SkillDto> getSkillList() {

		return skillsRepo.findAll();
	}

	@Override
	public SkillDto findBySkillId(int skillId) {

		return skillsRepo.findBySkillid(skillId);
	}

	/**
	 * 
	 * @param skillId
	 * @param skill
	 * @return updated skill details
	 * @Discription This method perform an update action if the data is present else
	 *              it creates new resource
	 */
	@Override
	public SkillDto updateSkill(int skillId, Skills skill) {

		SkillDto existSkillDto = skillsRepo.findBySkillid(skillId);
		if (existSkillDto == null) {
			SkillDto newSkillDto = new SkillDto();
			newSkillDto.setSkillName(skill.getSkillName());
			newSkillDto.setSkillid(skillId);
			skillsRepo.save(newSkillDto);
			return newSkillDto;
		} else {
			existSkillDto.setSkillName(skill.getSkillName());
			skillsRepo.save(existSkillDto);
		}
		return existSkillDto;
	}

	@Override
	public SkillDto deleteSkill(String skillname) throws RecordNotFoundException {

		SkillDto skillToBeDeleted = skillsRepo.findBySkillName(skillname);
		if (skillToBeDeleted == null)
			throw new RecordNotFoundException("Record not found with Skill name " + skillname);
		skillsRepo.delete(skillToBeDeleted);
		return skillToBeDeleted;
	}
}
