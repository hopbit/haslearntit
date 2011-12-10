/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.entry;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;


import org.joda.time.Duration;
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
    
    @Test
	public void shouldReturnLearningTimeInMinutes() {
    	entry.itTookInMinutes(10);
    	
    	Duration learingDuration = entry.getLearingDuration();
    	
    	assertEquals(Duration.standardMinutes(10), learingDuration);
	}
    

}
