package it.haslearnt.controller;

import it.haslearnt.service.UserStaticticsService;

import org.springframework.web.servlet.ModelAndView;

public class StaticticsController {

    private UserStaticticsService userStaticticsService;

    public StaticticsController(UserStaticticsService userStaticticsService) {
        this.userStaticticsService = userStaticticsService;
    }

    public ModelAndView getTotalLearningTime(String userName) {

        final ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("hours", userStaticticsService.getHoursForUser(userName));
        return modelAndView;
    }

}
