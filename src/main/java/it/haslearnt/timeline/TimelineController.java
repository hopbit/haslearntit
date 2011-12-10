package it.haslearnt.timeline;

import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimelineController {

	@Autowired
	EntryRepository entryRepository;
	@Resource(name = "userAuthenticationInBackend")
	public UserAuthenticationInBackend userAuthenticationInBackend;

	@RequestMapping("/")
	public ModelAndView mainTimelineView() {
		ModelAndView mav = new ModelAndView("timeline");
		List<Entry> entries = new ArrayList<Entry>();
		User loggedUser = userAuthenticationInBackend.getLoggedUser();
		if (loggedUser != null) {
			String userName = loggedUser.name();
			entries = entryRepository.fetchForUser(userName);
			mav.addObject("user", loggedUser);
		}
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
