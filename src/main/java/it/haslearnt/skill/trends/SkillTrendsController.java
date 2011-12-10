package it.haslearnt.skill.trends;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.google.common.base.*;

@Controller
public class SkillTrendsController {
	@Autowired
	SkillTrendsRepository repository;
	Integer numberTop = 5;

	@RequestMapping(method = RequestMethod.GET, value = "/skill/trends")
	public @ResponseBody
	String showTrends() {
		List<SkillTrend> skillTrends = repository.loadTop(numberTop);

		ArrayList<String> skillTrendsJsons = new ArrayList<String>();
		for (SkillTrend trend : skillTrends) {
			skillTrendsJsons.add(toJson(trend));
		}
		return "{ \"results\": [" + Joiner.on(", ").join(skillTrendsJsons) + "]}";
	}

	private String toJson(SkillTrend trend) {
		return "{" +
				" \"skill\": \"" + trend.skill() + "\",\n" +
				" \"learntBy\": " + trend.learntBy() +
				"}";
	}

}
