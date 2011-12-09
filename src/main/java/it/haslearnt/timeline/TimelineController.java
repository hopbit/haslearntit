package it.haslearnt.timeline;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class TimelineController {

	@RequestMapping("/")
	public String mainTimelineView() {
		return "timeline";
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
