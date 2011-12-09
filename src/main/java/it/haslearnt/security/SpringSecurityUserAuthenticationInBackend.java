package it.haslearnt.security;

import static org.springframework.util.Assert.*;
import it.haslearnt.commonExceptions.ThingThatShouldNotBeException;
import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;

import java.util.ArrayList;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUserAuthenticationInBackend implements UserAuthenticationInBackend {
    private UserRepository userRepository;

    public SpringSecurityUserAuthenticationInBackend(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method should be used only to right after registration or logging in tests
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean login(String userName, String internalHashKeyForAutomaticLoginAfterRegistration) {
        notNull(userName);
        boolean isLoginSuccesfull = false;
        User user = userRepository.load(userName);
        if (user != null) {
            AuthenticationUserDetails userDetails = new AuthenticationUserDetails(user);
            final RememberMeAuthenticationToken rememberMeAuthenticationToken = new RememberMeAuthenticationToken(
                    internalHashKeyForAutomaticLoginAfterRegistration, userDetails, new ArrayList());
            rememberMeAuthenticationToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(rememberMeAuthenticationToken);
            isLoginSuccesfull = true;
        }
        return isLoginSuccesfull;
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAuthenticated(authentication);
    }

    /**
     * Lightweight method to get currently logged authentication details.
     */
    @Override
    public AuthenticationUserDetails getLoggedUserDetails() {
        AuthenticationUserDetails loggedUserDetails = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthenticationUserDetails) {
                loggedUserDetails = ((AuthenticationUserDetails) principal);
            } else {
                throw new ThingThatShouldNotBeException(
                        "Expected class of authentication principal is AuthenticationUserDetails. Given: "
                                + principal.getClass());
            }
        }
        return loggedUserDetails;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    /**
     * Heavyweight method to get logged authentication. Remember that this method is touching the database, ergo it's
     * heavy. Use getLoggedUserDetails for fast and light access to logged authentication data.
     */
    @Override
    public User getLoggedUser() {
        User loggedUser = null;
        AuthenticationUserDetails userDetails = getLoggedUserDetails();
        if (userDetails != null) {
            loggedUser = userRepository.load(userDetails.getUsername());
        }
        return loggedUser;
    }
}
