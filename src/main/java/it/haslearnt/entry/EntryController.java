package it.haslearnt.entry;

import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsService;

import java.util.List;

import org.apache.log4j.Logger;
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

	Logger logger = Logger.getLogger(EntryController.class);
	@Autowired
	UserAuthenticationInBackend authenticationInBackend;
	@Autowired
	UserStaticticsService userStatisticsService;

	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public @ResponseBody
	String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty,
			@RequestParam Integer learningtime,
										@RequestParam(required = false) boolean completed
										) {
		logger.error(" >>>>>>>>>>>>>>>>>>>>>>>>>> Entering submit");
		Entry entry = new Entry().when(when).iveLearnt(text).andItWas(difficulty)
				.itTook(learningtime, Entry.TimeType.MINUTES);
		if (completed) {
			entry.andIveCompleted();
		}
		entry.build();
		entryRepository.save(entry);
		userStatisticsService.addLearningTimeForUser(authenticationInBackend.getLoggedUserDetails().getUsername(),
				learningtime);
		return "OK";
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
