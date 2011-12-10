package it.haslearnt.controller;

import it.haslearnt.commonExceptions.ResourceNotFoundException;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class UserTimerController {
    @Autowired(required = true)
    EntryRepository entryRepository;

    @Autowired(required = true)
    UserRepository userRepository;

    static String userTimeLineKey = "userTimeLine";

    public String showUserTimeline(String userName, Model model) {
        verifyUserExists(userName);
        List<Entry> userTimeLine = entryRepository.fetchForUser(userName);
        model.addAttribute(userTimeLineKey, userTimeLine);
        return userTimeLineKey;
    }

    private void verifyUserExists(String userName) {
        User user = userRepository.load(userName);
        if(user == null) {
            throw new ResourceNotFoundException("No user with user name " + userName);
        }
    }

}
