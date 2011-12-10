package it.haslearnt.timeline;

import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.statistics.UserStaticticsRepository;
import it.haslearnt.statistics.UserStatistics;
import it.haslearnt.user.User;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimelineController {

	@Autowired
	private EntryRepository entryRepository;
	@Resource(name = "userAuthenticationInBackend")
	private UserAuthenticationInBackend userAuthenticationInBackend;
	@Autowired
	private UserStaticticsRepository userStatisticsRepository;

	@RequestMapping("/")
	public ModelAndView mainTimelineView() {
		ModelAndView mav = new ModelAndView("timeline");
		User loggedUser = userAuthenticationInBackend.getLoggedUser();
		if (loggedUser != null) {
			String userName = loggedUser.name();
			List<Entry> entries = entryRepository.fetchForUser(userName);
			
			UserStatistics loadStatisticsForUser = userStatisticsRepository.loadStatisticsForUser(userName);
			mav.addObject("entries", entries);
			mav.addObject("user", loggedUser);
			mav.addObject("userStatistics", loadStatisticsForUser);
		}
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

	public void setEntryRepository(EntryRepository entryRepository) {
		this.entryRepository = entryRepository;
	}
	
	public void setUserAuthenticationInBackend(UserAuthenticationInBackend userAuthenticationInBackend) {
		this.userAuthenticationInBackend = userAuthenticationInBackend;
	}
	
	public void setUserStatisticsRepository(UserStaticticsRepository userStatisticsRepository) {
		this.userStatisticsRepository = userStatisticsRepository;
	}
}
