package it.haslearnt.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@Autowired
	EntryRepository entryRepository;


	@RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
	public @ResponseBody String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty, @RequestParam Integer learningtime) {
		entryRepository.save(new Entry().when(when).iveLearnt(text).andItWas(difficulty).itTook(learningtime, Entry.TimeType.MINUTES));
        return "OK";
	}
}
