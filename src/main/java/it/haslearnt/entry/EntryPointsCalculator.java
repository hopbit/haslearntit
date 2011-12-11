/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.entry;

import it.haslearnt.skill.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntryPointsCalculator {

	@Autowired
	SkillRepository skillRepository;

	public int calculate(String difficulty, int learningTime, String skill) {
		boolean isNewSkill = skillRepository.isNew(skill);
		return calculatePoints(learningTime, difficulty, isNewSkill);
	}


	private int calculatePoints(double learningTime, String difficulty, boolean isNewSkill) {
		int points = (int) Math.round(learningTime * difficultyFactor(difficulty));

		return isNewSkill ? points * 2 : points;
	}

	private double difficultyFactor(String difficulty) {
		Entry.DifficultyLevel level = Entry.DifficultyLevel.valueOf(difficulty);
		switch (level) {
			case easy:
				return 1;
			case medium:
				return 1.2;
			case hard:
				return 1.4;
			default:
				throw new IllegalStateException("Unsupported level " + difficulty);
		}
	}
}
