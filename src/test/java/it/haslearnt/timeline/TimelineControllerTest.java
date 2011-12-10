package it.haslearnt.timeline;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.SpringSecurityUserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsRepository;
import it.haslearnt.statistics.UserStatistics;
import it.haslearnt.user.User;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.server.result.MockMvcResultMatchers;

import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TimelineControllerTest {

	TimelineController controller = new TimelineController();
	ArrayList<Entry> entries = Lists.newArrayList(new Entry().today().iveLearnt("java").andItWas("easy"));
	@Mock
	UserStaticticsRepository userStatisticRepository;
	User user = new User().withName("user");
	@Mock
	private EntryRepository entryRepository;
	@Mock
	private SpringSecurityUserAuthenticationInBackend userAuthenticationInBackend;

	@Before
	public void setUp() throws Exception {
		controller.setEntryRepository(entryRepository);
		controller.setUserAuthenticationInBackend(userAuthenticationInBackend);
		controller.setUserStatisticsRepository(userStatisticRepository);
	}

	@Test
	public void shouldServeTimelineView() throws Exception {
		//given
		String ourUser = "user";
		
		UserStatistics userStatistics = new UserStatistics();
		when(userAuthenticationInBackend.getLoggedUser()).thenReturn(user);
		when(userStatisticRepository.loadStatisticsForUser(ourUser)).thenReturn(userStatistics);
		when(entryRepository.fetchForUser(ourUser)).thenReturn(entries);

		//when
		standaloneSetup(controller).build().perform(get("/"))
		
		//then
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("timeline"))
				.andExpect(MockMvcResultMatchers.model().attribute("entries", entries))
				.andExpect(MockMvcResultMatchers.model().attribute("user", user))
				.andExpect(MockMvcResultMatchers.model().attribute("userStatistics", userStatistics));
		
	}

	@Test
	public void shouldShowEmptyView4AnonymousUser() throws Exception {
		when(userAuthenticationInBackend.getLoggedUser()).thenReturn(null);

		standaloneSetup(controller).build().perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("timeline"))
				.andExpect(MockMvcResultMatchers.model().attribute("entries", new ArrayList()));
	}

}
