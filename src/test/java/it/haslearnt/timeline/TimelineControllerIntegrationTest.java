package it.haslearnt.timeline;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.Entry.TimeType;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.user.User;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.server.result.MockMvcResultMatchers;

import setup.IntegrationTest;

@Ignore
public class TimelineControllerIntegrationTest extends IntegrationTest {

	@Autowired
	TimelineController timelineController;

	@Autowired
	EntryRepository entryRepository;

	@Resource(name = "userAuthenticationInBackend")
	UserAuthenticationInBackend userAuthenticationInBackend;

	@Test
	public void shouldServeTimelineView() throws Exception {
		authenticateTestUser();

		Entry entry = new Entry().andItWas("medium").iveLearnt("Testowy entry").today().itTook(1, TimeType.MINUTES).build();
		entryRepository.saveEntry(entry, userAuthenticationInBackend.getLoggedUser().name());
		ArrayList<Entry> entries = new ArrayList<Entry>();
		entries.add(entry);

		final User expectedUser = userAuthenticationInBackend.getLoggedUser();
		standaloneSetup(timelineController)
				.build()
				.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("timeline"))
				.andExpect(MockMvcResultMatchers.model().attribute("entries", entries))
				.andExpect(
						MockMvcResultMatchers.model().attribute("user", new BaseMatcher<User>() {

							@Override
							public boolean matches(Object arg0) {
								User user = (User) arg0;
								return user.name().equals(expectedUser.name());
							}

							@Override
							public void describeTo(Description arg0) {

							}

						}));
	}
}
