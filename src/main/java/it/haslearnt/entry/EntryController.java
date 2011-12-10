package it.haslearnt.entry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntryController {

	static final String SUGGESTIONS_SKILLS_VIEW = "suggestionSkills";

	static final String FOUND_ENTRIES_KEY = "entries";

	static final String SKILL_KEY = "skill";

	@Autowired
	EntryRepository entryRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public String submit(@RequestParam String when, @RequestParam String text,
			@RequestParam String difficulty) {
		entryRepository.save(new Entry().when(when).iveLearnt(text)
				.andItWas(difficulty));
		return "timeline";
	}

	public String fetchEntryListByName(ModelMap model) {
		String skill = (String) model.get(SKILL_KEY);
		List<Entry> entries = entryRepository.fetchEntriesBySkillName(skill);
		model.put(FOUND_ENTRIES_KEY, entries);
		return SUGGESTIONS_SKILLS_VIEW;
	}
}
