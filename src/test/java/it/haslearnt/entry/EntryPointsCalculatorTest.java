/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.entry;

import it.haslearnt.skill.SkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class EntryPointsCalculatorTest {

	private Entry sampleEntry = new Entry().today().iveLearnt("java");

	@Mock
	private SkillRepository skillRepository;

	private EntryPointsCalculator sut;

	@Before
	public void init() {
		sut = new EntryPointsCalculator();
		sut.skillRepository = skillRepository;
		given(skillRepository.isNew("nazwaSkila")).willReturn(false);
	}

	@Test
	public void shouldAssignZeroPointsForZeroLearningTime() {
		//given
		String difficulty = Entry.DifficultyLevel.EASY.name();
		Entry entry = sampleEntry.andItWas(difficulty).itTookInMinutes(0);


		//when
		int points = sut.calculate(entry.getDifficulty(), entry.getLearningTime(), entry.getSkill());

		//then
		assertThat(points).isEqualTo(0);
	}

	@Test
	public void calculatePointsForEasy() {
		Entry entry = sampleEntry.andItWas(Entry.DifficultyLevel.EASY.name()).itTookInMinutes(10);

		//when
		int points = sut.calculate(entry.getDifficulty(), entry.getLearningTime(), entry.getSkill());

		//then
		assertThat(points).isEqualTo(10);
	}

	@Test
	public void calculatePointsForMedium() {
		Entry entry = sampleEntry.andItWas(Entry.DifficultyLevel.MEDIUM.name()).itTookInMinutes(10);

	//when
	int points = sut.calculate(entry.getDifficulty(), entry.getLearningTime(), entry.getSkill());

	//then
	assertThat(points).isEqualTo(12);

	}

	@Test
	public void calculatePointsForHard() {
		Entry entry = sampleEntry.andItWas(Entry.DifficultyLevel.HARD.name()).itTookInMinutes(10);

		//when
		int points = sut.calculate(entry.getDifficulty(), entry.getLearningTime(), entry.getSkill());

		//then
		assertThat(points).isEqualTo(14);
	}

	@Test
	public void calculateExtraPointForNewSkill() throws Exception {
		//given
		String skill = "nazwaSkila";
		given(skillRepository.isNew(skill)).willReturn(true);
		Entry entry = sampleEntry.andItWas(Entry.DifficultyLevel.EASY.name()).itTookInMinutes(10).iveLearnt(skill);

		//when
		int points = sut.calculate(entry.getDifficulty(), entry.getLearningTime(), entry.getSkill());

		//then
		assertThat(points).isEqualTo(20);
	}
}
