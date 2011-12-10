package it.haslearnt.entry;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import setup.IntegrationTest;

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
		Entry entry3 = new Entry().today().iveLearnt("C++").andItWas("hard");

		repository.saveEntry(entry, "tomek");
		repository.saveEntry(entry2, "tomek");
		repository.saveEntry(entry3, "rafal");

		List<Entry> fetchedEntries4Tomek = repository.fetchForUser("tomek");
		List<Entry> fetchedEntries4Rafal = repository.fetchForUser("rafal");

		assertThat(fetchedEntries4Tomek).containsOnly(entry, entry2);
		assertThat(fetchedEntries4Rafal).containsOnly(entry3);
	}
}
