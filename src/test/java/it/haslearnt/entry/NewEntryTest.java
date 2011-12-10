package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.haslearnt.entry.Entry.TimeType;

import java.util.*;

import org.fest.assertions.Assertions;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import setup.*;

public class NewEntryTest extends IntegrationTest {

	@Autowired
	EntryRepository repository;

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
	@Ignore
	public void fetchEntriesByName()  {
		String scala1 = "scala1";
		Entry scala = new Entry().iveLearnt(scala1).today().andItWas("hard");
		Entry java = new Entry().iveLearnt("java").today().andItWas("easy");
		repository.save(scala);
		repository.save(java);
		
		List<String> skills = repository.fetchEntriesBySkillName("scala");
		
		Assertions.assertThat(skills).containsOnly(scala1);
	}
}
