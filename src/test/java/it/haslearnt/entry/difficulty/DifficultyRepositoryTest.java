package it.haslearnt.entry.difficulty;

import static org.junit.Assert.assertEquals;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import setup.IntegrationTest;

public class DifficultyRepositoryTest extends IntegrationTest {

	@Autowired
	DifficultyRepository difficultyRepository;
	@Autowired
	EntryRepository entryRepository;

	@Test
	public void testDefaultDifficulty() {
		assertEquals("medium", difficultyRepository.getDifficultyFor("ruby"));

	}

	@Test
	public void testEasyAndHartToMediumDifficulty() {
		String skill = "java";
		String easy = "easy";
		entryRepository.saveEntry(createEntry(skill, easy), "username");
		entryRepository.saveEntry(createEntry(skill, easy), "username");
		entryRepository.saveEntry(createEntry(skill, "medium"), "username");

		assertEquals(easy, difficultyRepository.getDifficultyFor(skill));

	}

	private Entry createEntry(String skill, String easy) {
		return new Entry().iveLearnt(skill).andItWas(easy).today().itTookInMinutes(1).build();
	}
}
