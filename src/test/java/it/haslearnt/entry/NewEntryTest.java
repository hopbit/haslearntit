package it.haslearnt.entry;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

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

        repository.saveEntry(entry, "tomek");
        repository.saveEntry(entry2, "tomek");

        List<Entry> fetchedEntries = repository.fetchForUser("tomek");

        assertThat(fetchedEntries).containsOnly(entry, entry2);
    }
}
