package it.haslearnt.statistics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticticsController {

    @Autowired
    UserStaticticsRepository userStaticticsService;

    public StaticticsController() {
    }

    public ModelAndView getTotalLearningTime(String userName) {

        final ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("hours", userStaticticsService.getHoursForUser(userName));
        return modelAndView;
    }

}
