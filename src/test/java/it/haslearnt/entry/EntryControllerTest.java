package it.haslearnt.entry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;

public class EntryControllerTest {

	@Test
	public void shouldSubmitNewEntry() throws Exception {
		EntryRepository entryRepository = mock(EntryRepository.class);
		EntryController entryController = new EntryController();
		entryController.entryRepository = entryRepository;
		standaloneSetup(entryController).build()
				.perform(post("/entry/submit").param("text", "new skill").param("when", "yesterday")
                        .param("difficulty", "easy").param("learningtime", "20"))
				.andExpect(status().isOk());

		verify(entryRepository).save(new Entry().when("yesterday").iveLearnt("new skill").andItWas("easy").itTook(20, Entry.TimeType.MINUTES));
	}
}
