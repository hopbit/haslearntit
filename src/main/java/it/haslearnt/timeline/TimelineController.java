package it.haslearnt.timeline;

import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.user.User;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimelineController {

	public EntryRepository entryRepository;
	public UserAuthenticationInBackend securityBackend;

	@RequestMapping("/")
	public ModelAndView mainTimelineView() {
		User loggedUser = securityBackend.getLoggedUser();
		List<Entry> entries = entryRepository.fetchForUser(loggedUser.name());
		ModelAndView mav = new ModelAndView("timeline");
		// List<Entry> entries = Lists.newArrayList(new
		// Entry().today().iveLearnt("java").andItWas("hard"));
		mav.addObject("entries", entries);
		mav.addObject("user", loggedUser);
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
