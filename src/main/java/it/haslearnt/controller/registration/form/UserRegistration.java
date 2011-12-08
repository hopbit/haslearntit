/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.controller.registration.form;

import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserRegistration {

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String getRegistrationForm(Model model) {
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return "registration/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@Valid UserRegistrationForm form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("userRegistrationForm", form);
            return "redirect:registration";
        }
        User user = new User()
                .withName(form.getName())
                .withEmail(form.getEmail())
                .withPassword(form.getPassword());

        userRepository.save(user);
        return "redirect:/user/" + form.getName();
    }
}
