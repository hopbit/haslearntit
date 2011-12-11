package it.haslearnt.entry.difficulty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DifficultyControler {

	private DifficultyRepository difficultyRepository;

	@Autowired
	public void setDifficultyRepository(DifficultyRepository difficultRepository) {
		this.difficultyRepository = difficultRepository;
	}

	@RequestMapping("/skill/difficulty")
	@ResponseBody
	public String getDifficultyLevel(@RequestParam("skill") String skill) {
		System.out.println("getDifficultyLevel skill return: " + difficultyRepository.getDifficultyFor(skill));
		return difficultyRepository.getDifficultyFor(skill);
	}

}
