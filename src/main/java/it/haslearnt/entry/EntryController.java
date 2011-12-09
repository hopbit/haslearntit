package it.haslearnt.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntryController {

	@Autowired
	EntryRepository entryRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty) {
		entryRepository.save(new Entry().when(when).iveLearnt(text).andItWas(difficulty));
		return "timeline";
	}
}
