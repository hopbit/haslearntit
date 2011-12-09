package it.haslearnt.security;

import it.haslearnt.user.User;

public interface UserAuthenticationInBackend {
    boolean login(String userId, String internalHashKeyForAutomaticLoginAfterRegistration);

    void logout();

    boolean isLoggedIn();

    AuthenticationUserDetails getLoggedUserDetails();

    User getLoggedUser();
}
