package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import it.haslearnt.entry.Entry.TimeType;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import setup.IntegrationTest;
import setup.*;
import static junit.framework.Assert.assertTrue;

public class EntryRepositoryTest extends IntegrationTest {

	@Autowired
	EntryRepository repository;
	private String user = "tomrek";

	@Test
	public void saveNewEntry() {
		Entry entry = new Entry().iveLearnt("something").today().andItWas("easy").itTook(10, TimeType.MINUTES).build();

		repository.save(entry);

		assertNotNull(entry.id());
		Entry fetchedEntry = repository.load(entry.id());
		assertNotNull(fetchedEntry);
		assertEquals("something", fetchedEntry.what());
		assertEquals("today", fetchedEntry.when());
		assertEquals("easy", fetchedEntry.howDifficult());
		assertEquals(10, fetchedEntry.points());
	}
	
	@Test
	public void saveEntriesWithSkills() {
		final String skillName1 = "something";
		final String skillName2 = "somethingDifferent";
		Entry entry1 = new Entry().iveLearnt(skillName1).today().andItWas("easy").itTook(10, TimeType.MINUTES).build();
		Entry entry2 = new Entry().iveLearnt(skillName2).today().andItWas("easy").itTook(10, TimeType.MINUTES).build();

		repository.saveEntry(entry1, user);
		repository.saveEntry(entry2, user);
		
		List<String> skills = repository.fetchSkills();
		assertThat(skills).containsOnly(skillName1, skillName2);
	}
	
	@Test
	public void saveEntriesWithSameSkill() {
		final String skillName = "something";
		Entry entry1 = new Entry().iveLearnt(skillName).today().andItWas("easy").itTook(10, TimeType.MINUTES).build();
		Entry entry2 = new Entry().iveLearnt(skillName).today().andItWas("easy").itTook(10, TimeType.MINUTES).build();

		repository.saveEntry(entry1, user);
		repository.saveEntry(entry2, user);
		
		List<String> skills = repository.fetchSkills();
		assertThat(skills).containsOnly(skillName);
	}


	@Test
	public void fetchEntryForUser() {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("hard").build();
		Entry entry2 = new Entry().today().iveLearnt("net").andItWas("hard").build();
		Entry entry3 = new Entry().today().iveLearnt("C++").andItWas("hard").build();

		repository.saveEntry(entry, "tomek");
		repository.saveEntry(entry2, "tomek");
		repository.saveEntry(entry3, "rafal");

		List<Entry> fetchedEntries4Tomek = repository.fetchForUser("tomek");
		List<Entry> fetchedEntries4Rafal = repository.fetchForUser("rafal");

		assertThat(fetchedEntries4Tomek).containsOnly(entry, entry2);
		assertThat(fetchedEntries4Rafal).containsOnly(entry3);
	}
	

    @Test
    public void saveNewEntryWithSkillNotCompleted() {
        // given
        // entry already prepared
        Entry entry = new Entry().iveLearnt("something").today().andItWas("easy").build();

        //when
        repository.save(entry);

        //then
        assertNotNull(entry.id());
        Entry fetchedEntry = repository.load(entry.id());
        assertNotNull(fetchedEntry);
        assertFalse(fetchedEntry.isCompleted());
    }

     @Test
    public void saveNewEntryWithSkillCompleted() {
        // given

        Entry entry = new Entry().iveLearnt("something").today().andItWas("easy").andIveCompleted().build();

        //when
        repository.save(entry);

        //then
        assertNotNull(entry.id());
        Entry fetchedEntry = repository.load(entry.id());
        assertNotNull(fetchedEntry);
        assertTrue(fetchedEntry.isCompleted());
    }
}
