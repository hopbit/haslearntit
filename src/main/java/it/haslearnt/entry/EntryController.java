package it.haslearnt.entry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class EntryController {

	static final String SUGGESTIONS_SKILLS_VIEW = "suggestionSkills";

	static final String FOUND_SKILLS_KEY = "entries";

	static final String SKILL_KEY = "skill";

	@Autowired
	EntryRepository entryRepository;


	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public @ResponseBody String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty, @RequestParam Integer learningtime,
                                        @RequestParam(required = false) boolean completed
                                       ) {
        Entry entry = new Entry().when(when).iveLearnt(text).andItWas(difficulty).itTook(learningtime, Entry.TimeType.MINUTES);
        entryRepository.save(entry);
         if (completed) {
            entry.andIveCompleted();
        }
        return "OK";
    }

	public String fetchEntryListByName(ModelMap model) {
		String skill = (String) model.get(SKILL_KEY);
		List<String> suggestedSkills = entryRepository.fetchEntriesBySkillName(skill);
		model.put(FOUND_SKILLS_KEY, suggestedSkills);
		return SUGGESTIONS_SKILLS_VIEW;
	}

    /*

static final String SUGGESTIONS_SKILLS_VIEW = "suggestionSkills";

static final String FOUND_SKILLS_KEY = "entries";

static final String SKILL_KEY = "skill";

@Autowired
EntryRepository entryRepository;

@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
public String submit(@RequestParam String when, @RequestParam String text,
@RequestParam String difficulty, @RequestParam(required = false) boolean completed) {

        Entry entry = new Entry().when(when).iveLearnt(text)
                .andItWas(difficulty);
        if (completed) {
            entry.andIveCompleted();
        }

        entryRepository.save(entry);

        return "timeline";
}

public String fetchEntryListByName(ModelMap model) {
String skill = (String) model.get(SKILL_KEY);
List<String> suggestedSkills = entryRepository.fetchEntriesBySkillName(skill);
model.put(FOUND_SKILLS_KEY, suggestedSkills);
return SUGGESTIONS_SKILLS_VIEW;
}
     */
}
