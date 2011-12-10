package it.haslearnt.controller;

import it.haslearnt.commonExceptions.ResourceNotFoundException;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.scale7.cassandra.pelops.exceptions.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserTimelineController {
    @Autowired(required = true)
    EntryRepository entryRepository;

    @Autowired(required = true)
    UserRepository userRepository;

    static String userTimeLineKey = "userTimeline";

    @RequestMapping(method = RequestMethod.GET)
    public String showUserTimeline(String userName, Model model) {
        verifyUserExists(userName);
        List<Entry> userTimeLine = entryRepository.fetchForUser(userName);
        model.addAttribute(userTimeLineKey, userTimeLine);
        return userTimeLineKey;
    }

    private void verifyUserExists(String userName) {
        try {
            User user = userRepository.load(userName);
            if (user == null) {
                throw new ResourceNotFoundException("No user with user name " + userName);
            }
        } catch (ModelException e) {
            throw new ResourceNotFoundException("No user with user name " + userName, e);
        }
    }

}
