package it.haslearnt.timeline;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.SpringSecurityUserAuthenticationInBackend;
import it.haslearnt.user.User;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.server.result.MockMvcResultMatchers;

import com.google.common.collect.Lists;
import org.springframework.web.servlet.ModelAndView;

public class TimelineControllerTest {

	TimelineController controller = new TimelineController();

	ArrayList<Entry> entries = Lists.newArrayList(new Entry().today().iveLearnt("java").andItWas("easy"));
	User user = new User().withName("user");

	@Before
	public void setUp() throws Exception {
		controller.entryRepository = mock(EntryRepository.class);
		controller.userAuthenticationInBackend = mock(SpringSecurityUserAuthenticationInBackend.class);
	}

	@Test
    @Ignore
	public void shouldServeTimelineView() throws Exception {
		when(controller.userAuthenticationInBackend.getLoggedUser()).thenReturn(user);
		when(controller.entryRepository.fetchForUser("user")).thenReturn(entries);

		standaloneSetup(controller).build().perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("timeline"))
				.andExpect(MockMvcResultMatchers.model().attribute("entries", entries))
				.andExpect(MockMvcResultMatchers.model().attribute("user", user));
	}

    @Test
    public void shouldServeTimelineEntries() throws Exception {

        ModelAndView modelAndView = controller.mainTimelineView();

        List<EntryDto> entries1 = (List) modelAndView.getModelMap().get("entries");
        Assertions.assertThat(entries1).hasSize(1);
    }

}
