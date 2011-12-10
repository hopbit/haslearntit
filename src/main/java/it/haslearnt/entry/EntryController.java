package it.haslearnt.entry;

import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

@Controller
public class EntryController {

	static final String SUGGESTIONS_SKILLS_VIEW = "suggestionSkills";

	static final String FOUND_SKILLS_KEY = "entries";

	static final String SKILL_KEY = "skill";

	@Autowired
	EntryRepository entryRepository;

	@Autowired
	UserAuthenticationInBackend authenticationInBackend;

	@Autowired
	UserStaticticsRepository userStatisticsService;

	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public @ResponseBody
	String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty,
			@RequestParam Integer learningtime, @RequestParam(required = false) boolean completed) {

		Entry entry = buildEntry(when, text, difficulty, learningtime, completed);
		entryRepository.saveEntry(entry, getLoggedUsername());
		userStatisticsService.addLearningTimeForUser(getLoggedUsername(),
				learningtime);
		return "OK";
	}

	private Entry buildEntry(String when, String text, String difficulty, Integer learningtime, boolean completed) {
		Entry entry = new Entry().when(when).iveLearnt(text).andItWas(difficulty)
				.itTook(learningtime, Entry.TimeType.MINUTES);
		if (completed) {
			entry.andIveCompleted();
		}
		entry.build();
		return entry;
	}

	protected String getLoggedUsername() {
		return authenticationInBackend.getLoggedUserDetails().getUsername();
	}

	@ResponseBody
	public String fetchSuggestedSkills(@RequestParam String prefix) {
		List<String> allSkills = entryRepository.fetchSkills();
		List<String> matchingSkills = findMatchingSkills(prefix.toLowerCase(), allSkills);
		return new Gson().toJson(matchingSkills);
	}

	private List<String> findMatchingSkills(String prefix,
			List<String> skills) {
		List<String> resultSkills = Lists.newArrayList();
		for (String skill : skills) {
			if (skill.startsWith(prefix)) {
				resultSkills.add(skill);
			}
		}
		return resultSkills;
	}
}
