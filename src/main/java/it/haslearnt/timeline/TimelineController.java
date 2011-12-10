package it.haslearnt.timeline;

import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.UserAuthenticationInBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TimelineController {

	@Autowired
	public EntryRepository entryRepository;
	@Autowired
	public UserAuthenticationInBackend securityBackend;

	@RequestMapping("/")
	public ModelAndView mainTimelineView() {
		ModelAndView mav = new ModelAndView("timeline");
        List<EntryDto> entries = new ArrayList<EntryDto>();
        entries.add(new EntryDto("dupa"));
        mav.addObject("entries", entries);
		return mav;
	}

	@RequestMapping("/user")
	public String userView() {
		return "user";
	}

	@RequestMapping("/logout")
	public String logoutView() {
		return "login";
	}

}
