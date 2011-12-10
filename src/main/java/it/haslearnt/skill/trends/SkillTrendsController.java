package it.haslearnt.skill.trends;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class SkillTrendsController {
	@Autowired
	SkillTrendsRepository repository;
	Integer numberTop = 5;

	@RequestMapping(method = RequestMethod.GET, value = "/skill/trends")
	public ModelAndView showTrends() {
		List<SkillTrend> skillTrends = repository.loadTop(numberTop);

		return new ModelAndView("timeline", Collections.singletonMap("skills", skillTrends));
	}

}
