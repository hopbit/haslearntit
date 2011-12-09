package it.haslearnt.timeline;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.server.result.MockMvcResultMatchers;

public class TimelineControllerTest {

    @Test
    public void shouldServeTimelineView() throws Exception {
        standaloneSetup(new TimelineController()).build()
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("timeline"));
    }
}
