package it.haslearnt.entry;

import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

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
            @RequestParam Integer learningtime,
            @RequestParam(required = false) boolean completed
            ) {
        Entry entry = new Entry().when(when).iveLearnt(text).andItWas(difficulty)
                .itTook(learningtime, Entry.TimeType.MINUTES);
        entryRepository.save(entry);

        userStatisticsService.addLearningTimeForUser(authenticationInBackend.getLoggedUserDetails().getUsername(),
                learningtime);

        if (completed) {
            entry.andIveCompleted();
        }
        return "OK";
    }

    public String fetchEntryListByName(ModelMap model) {
        String prefix = (String) model.get(SKILL_KEY);
        List<String> allSkills = entryRepository.fetchSkills();
        model.put(FOUND_SKILLS_KEY, findMatchingSkills(prefix, allSkills));
        return SUGGESTIONS_SKILLS_VIEW;
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
    /*
     * @RequestMapping(method = RequestMethod.POST, value = "/entry/submit") public String submit(@RequestParam String
     * when, @RequestParam String text,
     * 
     * @RequestParam String difficulty, @RequestParam(required = false) boolean completed) {
     * 
     * Entry entry = new Entry().when(when).iveLearnt(text) .andItWas(difficulty); if (completed) {
     * entry.andIveCompleted(); }
     * 
     * entryRepository.save(entry);
     * 
     * return "timeline"; }
     */
}
