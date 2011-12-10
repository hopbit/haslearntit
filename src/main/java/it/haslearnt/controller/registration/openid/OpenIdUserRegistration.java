/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.controller.registration.openid;

import static org.apache.commons.lang.StringUtils.isEmpty;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.security.openid.NormalizedOpenIdAttributes;
import it.haslearnt.session.SessionKeys;
import it.haslearnt.user.User;
import it.haslearnt.user.UserOpenId;
import it.haslearnt.user.UserOpenIdRepository;
import it.haslearnt.user.UserRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/openIdRegistration")
public class OpenIdUserRegistration {
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Autowired
	@Qualifier("userOpenIdRepository")
	private UserOpenIdRepository userOpenIdRepository;

	@Autowired
	private UserAuthenticationInBackend userAuthenticationInBackend;

	@RequestMapping(method = RequestMethod.GET)
	public String getRegistrationForm(Model model, HttpSession session) {
		NormalizedOpenIdAttributes normalizedOpenIdAttributes = (NormalizedOpenIdAttributes) session
				.getAttribute(SessionKeys.openIdAttributes);
		throwExceptionIfNoOpenId(normalizedOpenIdAttributes);
		UserOpenIdRegistrationForm form = createPrepopulatedForm(normalizedOpenIdAttributes);
		model.addAttribute("userRegistrationForm", form);
		return "registration/openIdForm";
	}

	private UserOpenIdRegistrationForm createPrepopulatedForm(NormalizedOpenIdAttributes normalizedOpenIdAttributes) {
		UserOpenIdRegistrationForm form = new UserOpenIdRegistrationForm();
		String email = normalizedOpenIdAttributes.getEmailAddress();
		String name = normalizedOpenIdAttributes.getLoginReplacement();
		form.setEmail(email);
		form.setName(name);
		return form;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid UserOpenIdRegistrationForm form, Errors errors, Model model, HttpSession session) {
		if (errors.hasErrors()) {
			model.addAttribute("userRegistrationForm", form);
			return "redirect:registration";
		}

		NormalizedOpenIdAttributes normalizedOpenIdAttributes = (NormalizedOpenIdAttributes) session
				.getAttribute(SessionKeys.openIdAttributes);
		throwExceptionIfNoOpenId(normalizedOpenIdAttributes);
		saveUser(form);
		saveOpenIdUser(form, normalizedOpenIdAttributes);
		userAuthenticationInBackend.login(form.getName(), "lipny hash");
		return "redirect:/user?userName=" + form.getName();
	}

	private void saveUser(UserOpenIdRegistrationForm form) {
		User user = new User()
				.withName(form.getName())
				.withEmail(form.getEmail())
				.withPassword("You will never gonna get it");
		userRepository.save(user);
	}

	private void saveOpenIdUser(UserOpenIdRegistrationForm form, NormalizedOpenIdAttributes normalizedOpenIdAttributes) {
		UserOpenId userOpenId = new UserOpenId(normalizedOpenIdAttributes.getUserLocalIdentifier())
				.withName(form.getName());
		userOpenIdRepository.save(userOpenId);
	}

	private void throwExceptionIfNoOpenId(NormalizedOpenIdAttributes normalizedOpenIdAttributes) {
		if (normalizedOpenIdAttributes == null || isEmpty(normalizedOpenIdAttributes.getLoginReplacement())) {
			throw new RuntimeException(
					"Could not find OpenId attributes in session. Perhaps you've come here without visiting your OpenId provider?");
		}
	}

}
