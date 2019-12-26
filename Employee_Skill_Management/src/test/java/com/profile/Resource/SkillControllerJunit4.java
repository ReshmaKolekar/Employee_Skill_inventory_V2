package com.profile.Resource;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.dto.SkillDto;
import com.profile.model.SkillList;
import com.profile.model.Skills;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillControllerJunit4 {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getSkillList() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rest/skill/skillList")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	public void addSkill() throws Exception {

		SkillList skill = new SkillList();

		List<Skills> skillList = new ArrayList<Skills>();

		Skills skillJava = new Skills();
		skillJava.setSkillName("java");
		skillJava.setExperiance(5);

		Skills skillDotNet = new Skills();
		skillDotNet.setSkillName("dot net");
		skillDotNet.setExperiance(6);

		skillList.add(skillJava);
		skillList.add(skillDotNet);

		skill.setSkilllist(skillList);

		String jsonString = om.writeValueAsString(skill);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/skill/addSkills")
				.accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(200, response.getStatus());
	}

}
