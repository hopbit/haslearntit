package it.haslearnt.entry;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.*;
import it.haslearnt.security.AuthenticationUserDetails;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsService;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EntryControllerTest {

	private EntryController entryController = new EntryController();
	private EntryRepository entryRepository = mock(EntryRepository.class);
	private AuthenticationUserDetails loggedUserDetails = mock(AuthenticationUserDetails.class);

	@Before
	public void setupController() {
		entryController.entryRepository = entryRepository;
		entryController.authenticationInBackend = mock(UserAuthenticationInBackend.class);
		entryController.userStatisticsService = mock(UserStaticticsService.class);
		when(entryController.authenticationInBackend.getLoggedUserDetails()).thenReturn(loggedUserDetails);
		when(loggedUserDetails.getUsername()).thenReturn("user");
	}

	@Test
	public void shouldSubmitNewEntry() throws Exception {
		standaloneSetup(entryController)
				.build()
				.perform(
						post("/entry/submit").param("text", "new skill").param("when", "yesterday")
								.param("difficulty", "easy").param("learningtime", "20"))
				.andExpect(status().isOk());

		verify(entryRepository).save(
				new Entry().when("yesterday").iveLearnt("new skill").andItWas("easy")
						.itTook(20, Entry.TimeType.MINUTES).build());
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
						.andItWas("easy").andIveCompleted().build());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldFetchSuggestedSkillsBasicCase() throws Exception {
		String foundSkill = "scala1";
		mockRepositoryShouldFetchSkills(foundSkill, "java");

		String suggestedSkills = entryController.fetchSuggestedSkills("scala");

		List<String> skills = getSkillsFromJson(suggestedSkills);
		assertThat(skills).containsOnly(foundSkill);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldFetchSuggestedSkillsVerifyCaseInsensitive() throws Exception {
		String foundSkill = "scala123";
		mockRepositoryShouldFetchSkills(foundSkill, "java");

		String suggestedSkills = entryController.fetchSuggestedSkills("ScAlA");

		List<String> skills = getSkillsFromJson(suggestedSkills);
		assertThat(skills).containsOnly(foundSkill);
	}

	private void mockRepositoryShouldFetchSkills(String... skills) {
		when(entryRepository.fetchSkills()).thenReturn(Arrays.asList(skills));
	}

	private List<String> getSkillsFromJson(String suggestedSkills) {
		return new Gson().fromJson(suggestedSkills, new TypeToken<List<String>>() {
		}.getType());
	}

	@Test
	public void shouldUpdateTotalLearningTimeForUser() throws Exception {

		standaloneSetup(entryController)
				.build()
				.perform(
						post("/entry/submit")
								.param("text", "new skill")
								.param("when", "yesterday")
								.param("difficulty", "easy")
								.param("learningtime", "100")
								.param("completed", "true")
				)
				.andExpect(status().isOk());

		verify(entryController.userStatisticsService).addLearningTimeForUser("user", 100);

	}

	private ModelMap createInputModel(String skillName) {
		ModelMap model = new ModelMap();
		model.put(EntryController.SKILL_KEY, skillName);
		return model;
	}
}
