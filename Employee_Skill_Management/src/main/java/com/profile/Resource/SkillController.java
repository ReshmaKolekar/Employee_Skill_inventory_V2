package com.profile.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.profile.CustomException.InternalServerError;
import com.profile.CustomException.RecordNotFoundException;
import com.profile.dao.EmpSkillDaoImpl;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;
import com.profile.service.SkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Reshma
 * @Desciption This Controller contains Skill API's
 *
 */
@RestController
@RequestMapping("/rest/skill")
@Api(value = "Skill Resource ", description = "Skill data management")
public class SkillController {

	@Autowired
	EmpSkillDaoImpl empSkillDaoImpl;

	@Autowired
	SkillService skillService;

	/**
	 * 
	 * @param skillList
	 * @return list of saved skillset
	 */
	@ApiOperation(value = "Add Skills")
	@PostMapping("/addSkills")
	public ResponseEntity<List<SkillDto>> addSkill(@RequestBody SkillList skillList) {

		List<SkillDto> skillDtoList = skillService.addSkill(skillList);
		return ResponseEntity.ok().body(skillDtoList);
	}

	/**
	 * 
	 * @return SkillList
	 */
	@ApiOperation(value = "Returns all skills ")
	@GetMapping("/skillList")
	public ResponseEntity<List<SkillDto>> getSkillList() {

		List<SkillDto> skillDtoList = skillService.getSkillList();
		return ResponseEntity.ok().body(skillDtoList);
	}

	/**
	 * 
	 * @param id
	 * @return Skill details of specified SkillID
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Returns skill data by id ")
	@GetMapping("/getskilldetails/{skillId}")
	public ResponseEntity<SkillDto> findBySkillId(@PathVariable(value = "skillId") int skillId)
			throws RecordNotFoundException {

		SkillDto skillsDto = skillService.findBySkillId(skillId);
		if (skillsDto == null)
			throw new RecordNotFoundException("Record not found with skill id " + skillId);
		return ResponseEntity.ok().body(skillsDto);
	}

	/**
	 * 
	 * @param skillId
	 * @param skill
	 * @return updated skill details
	 * @throws InternalServerError
	 */
	@ApiOperation(value = "Update skill")
	@PutMapping("/update/skill/{skillId}")
	public ResponseEntity<?> updateSkill(@PathVariable(value = "skillId") int skillId, @RequestBody Skills skill)
			throws InternalServerError {

		SkillDto skillDto = skillService.updateSkill(skillId, skill);
		if (skillDto == null)
			throw new InternalServerError(" Failure while updating skill for skill " + skillId);
		return ResponseEntity.ok().body(skillDto);
	}

	/**
	 * 
	 * @param skillname
	 * @return Skill to be deleted
	 * @throws RecordNotFoundException
	 */
	@ApiOperation(value = "Delete skill")
	@DeleteMapping("/delete/skill/{skillname}")
	public ResponseEntity<String> delete(@PathVariable(value = "skillname") String skillname)
			throws RecordNotFoundException {

		SkillDto skill = skillService.deleteSkill(skillname);
		return ResponseEntity.ok().body(skill + " \n" + " is deleted");
	}

}
