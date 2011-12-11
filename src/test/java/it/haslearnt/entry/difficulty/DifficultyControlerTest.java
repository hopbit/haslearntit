package it.haslearnt.entry.difficulty;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

public class DifficultyControlerTest {

	@Test
	public void shouldReturnDifficultForSkill() throws Exception {
		// given
		String skill = "java";
		String difficult = "easy";
		DifficultyRepository repoMock = Mockito.mock(DifficultyRepository.class);
		when(repoMock.getDifficultyFor(skill)).thenReturn(difficult);
		DifficultyControler controler = new DifficultyControler();
		controler.setDifficultyRepository(repoMock);
		
		// when
		String text = controler.getDifficultyLevel(skill);
		
		// then
		assertEquals(difficult, text);
	}
}
