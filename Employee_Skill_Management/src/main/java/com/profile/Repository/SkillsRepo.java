package com.profile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profile.dto.SkillDto;
import com.sun.xml.bind.v2.model.core.ID;

public interface SkillsRepo extends JpaRepository<SkillDto, ID> {

	public SkillDto findBySkillName(String skillName);

	public SkillDto findBySkillid(int skillid);

}
