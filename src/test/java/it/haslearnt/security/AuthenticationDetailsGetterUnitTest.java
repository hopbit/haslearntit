/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.security;

import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationDetailsGetterUnitTest {

	@Mock
	UserRepository userRepository;

    String name = "name";
	String email = "email";
	String hashedPassword = "oidjgs";

	@Test
	public void shouldLoadUserByUsername() {
		// given
		AuthenticationDetailsGetter authenticationDetailsGetter = new AuthenticationDetailsGetter(userRepository);
		User user = new User().withName(name).withEmail(email).withPassword(hashedPassword);
		given(userRepository.load(name)).willReturn(user);

		// when
		UserDetails userDetails = authenticationDetailsGetter.loadUserByUsername(name);

		// then
		assertEquals(name, userDetails.getUsername());
		assertEquals(hashedPassword, userDetails.getPassword());
		assertTrue(userDetails.isEnabled());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowExceptionIfUserNotFound() {
		// given
		AuthenticationDetailsGetter authenticationDetailsGetter = new AuthenticationDetailsGetter(userRepository);

		// when
		authenticationDetailsGetter.loadUserByUsername(email);

		// then exception is thrown
	}
}
