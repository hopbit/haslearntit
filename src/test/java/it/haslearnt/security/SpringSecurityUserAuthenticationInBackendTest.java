package it.haslearnt.security;

import static org.junit.Assert.*;
import it.haslearnt.user.User;

import javax.annotation.Resource;

import org.junit.Test;

import predefinedData.UserPredefinedData;
import setup.IntegrationTest;

public class SpringSecurityUserAuthenticationInBackendTest extends IntegrationTest {
    @Resource(name = "userAuthenticationInBackend")
    UserAuthenticationInBackend userAuthenticationInBackend;

    @Test
    public void shouldReturnLoggedUser() {
        // given
        authenticateTestUser();

        // when
        User user = userAuthenticationInBackend.getLoggedUser();

        // then
        assertNotNull(user);
        assertEquals(UserPredefinedData.name, user.name());
    }

    @Test
    public void getLoggedUserShouldReturnNullWhenUserNotLogged() {
        // when
        User user = userAuthenticationInBackend.getLoggedUser();

        // then
        assertNull(user);
    }

    @Test
    public void shouldReturnLoggedUserDetails() {
        // when
        authenticateTestUser();

        // when
        AuthenticationUserDetails userDetails = userAuthenticationInBackend.getLoggedUserDetails();

        // then
        assertNotNull(userDetails);
        assertEquals(UserPredefinedData.name, userDetails.getUsername());
    }

    @Test
    public void getLoggedUserCredentialsShouldReturnNullWhenUserNotLogged() {
        // when
        AuthenticationUserDetails userDetails = userAuthenticationInBackend.getLoggedUserDetails();

        // then
        assertNull(userDetails);
    }

    @Test
    public void shouldAuthenticateOnlyByName() {
        // given
        saveUserInDatabase();

        // when
        boolean isAuthenticated = userAuthenticationInBackend.login(UserPredefinedData.name, "magicKey");

        // then
        assertTrue(isAuthenticated);
        assertTrue(userAuthenticationInBackend.isLoggedIn());
    }

    @Test
    public void shouldNotAuthenticateByWrongNamse() {
        // when
        boolean isAuthenticated = userAuthenticationInBackend.login("I don't have this account", "magicKey");
        // then
        assertFalse(isAuthenticated);
        assertFalse(userAuthenticationInBackend.isLoggedIn());
    }

    @Test
    public void shouldLogoutUser() {
        // given
        authenticateTestUser();

        // when
        userAuthenticationInBackend.logout();

        // then
        assertNull(userAuthenticationInBackend.getLoggedUser());
    }
}
