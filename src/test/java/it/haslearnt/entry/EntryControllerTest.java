package it.haslearnt.entry;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.*;

import org.junit.Test;

public class EntryControllerTest {

    @Test
    public void shouldSubmitNewEntry() throws Exception {
        EntryRepository entryRepository = mock(EntryRepository.class);
        standaloneSetup(new EntryController(entryRepository)).build()
                .perform(post("/entry/submit")
                        .param("text", "new skill")
                        .param("when", "today")
                        .param("difficulty", "easy")
                )
                .andExpect(status().isOk());

        verify(entryRepository).save(new Entry().today().iveLearnt("new skill").andItWas("easy"));
    }
}
