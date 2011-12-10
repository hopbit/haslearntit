package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		Entry entry = new Entry().iveLearnt("something").today().andItWas("hard");

		repository.save(entry);

		assertNotNull(entry.id());
		Entry fetchedEntry = repository.load(entry.id());
		assertNotNull(fetchedEntry);
		assertEquals("something", fetchedEntry.what());
		assertEquals("today", fetchedEntry.when());
		assertEquals("hard", fetchedEntry.howDifficult());
	}

	@Test
	public void fetchEntryForUser() {
		Entry entry = new Entry().today().iveLearnt("java").andItWas("hard");
		Entry entry2 = new Entry().today().iveLearnt("net").andItWas("hard");

		repository.saveEntry(entry, "tomek");
		repository.saveEntry(entry2, "tomek");

		List<Entry> fetchedEntries = repository.fetchForUser("tomek");

		assertThat(fetchedEntries).containsOnly(entry, entry2);
	}
	
	@Test
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
