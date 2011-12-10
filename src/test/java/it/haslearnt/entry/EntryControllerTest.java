package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

public class EntryControllerTest {

	EntryController entryController = new EntryController();
	EntryRepository entryRepository = mock(EntryRepository.class);

	@Before
	public void setupController(){
		entryController.entryRepository = entryRepository;		
	}
	
	@Test
	public void shouldSubmitNewEntry() throws Exception {
		EntryRepository entryRepository = mock(EntryRepository.class);
		EntryController entryController = new EntryController();
		entryController.entryRepository = entryRepository;
		standaloneSetup(entryController).build()
				.perform(post("/entry/submit").param("text", "new skill").param("when", "yesterday").param("difficulty", "easy").param("learningtime","20"))
				.andExpect(status().isOk());

		verify(entryRepository).save(new Entry().when("yesterday").iveLearnt("new skill").andItWas("easy").itTook(20, Entry.TimeType.MINUTES));
	}

	@Test
	public void shouldSubmitNewCompletedEntry() throws Exception {
		standaloneSetup(entryController)
				.build()
				.perform(
						post("/entry/submit")
                                .param("text", "new skill")
								.param("when", "yesterday")
								.param("difficulty", "easy")
                                .param("learningtime", "20")
								.param("completed", "true")
                )
				.andExpect(status().isOk());

		verify(entryRepository).save(
                new Entry().when("yesterday").iveLearnt("new skill").itTook(20, Entry.TimeType.MINUTES)
                        .andItWas("easy").andIveCompleted());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldFetchSkillsListByName() throws Exception {
		String suggestedSkill = "scala1";
		when(entryRepository.fetchSkills()).thenReturn( Arrays.asList(suggestedSkill, "java"));
		ModelMap model = createInputModel("scala");

		String fetchEntryListByName = entryController .fetchEntryListByName(model);
		
		List<String> skills = (List<String>) model.get(EntryController.FOUND_SKILLS_KEY);
		assertThat(EntryController.SUGGESTIONS_SKILLS_VIEW).isEqualTo(fetchEntryListByName);
		assertThat(skills).containsOnly(suggestedSkill);
	}

	private ModelMap createInputModel(String skillName) {
		ModelMap model = new ModelMap();
		model.put(EntryController.SKILL_KEY, skillName);
		return model;
	}
}
