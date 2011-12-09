package it.haslearnt.timeline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {

    @RequestMapping("/")
    public String mainTimelineView() {
        return "timeline";
    }

}
