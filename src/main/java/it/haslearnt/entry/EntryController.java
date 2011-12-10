package it.haslearnt.entry;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class EntryController {

	@Autowired
	EntryRepository entryRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty) {
		entryRepository.save(new Entry().when(when).iveLearnt(text).andItWas(difficulty).build());
		return "timeline";
	}
}
