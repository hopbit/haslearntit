package it.haslearnt.entry;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * @author <a href="mailto:tomasz.kramarczyk@agora.pl">Tomasz Kramarczyk</a>
 */
public class EntryTest {

    private Entry entry = new Entry();


    @Test
    public void testEntrySetSkillCompleted() {

        //when
        entry.andIveCompleted();

        //then
        assertTrue(entry.isCompleted());
    }

    @Test
    public void testEntrySetSkillNotYetCompleted() {

        assertFalse(entry.isCompleted());
    }

}
