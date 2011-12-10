package it.haslearnt.skill.trends;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.*;

import java.util.*;

import org.junit.*;

public class SkillTrendsControllerTest {

	@Test
	public void shouldLoadTopSkills() throws Exception {
		SkillTrendsController controller = new SkillTrendsController();
		SkillTrendsRepository repository = mock(SkillTrendsRepository.class);
		controller.repository = repository;

		List<SkillTrend> skillTrends = Arrays.asList(
				new SkillTrend().withSkill("skill1").learntBy(2),
				new SkillTrend().withSkill("skill2").learntBy(1));

		when(repository.loadTop(5)).thenReturn(skillTrends);

		standaloneSetup(controller).build()
				.perform(get("/skill/trends"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("skills", skillTrends));

		verify(repository).loadTop(5);
	}

}
