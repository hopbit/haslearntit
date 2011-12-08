/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package it.haslearnt.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import setup.IntegrationTest;

import static org.junit.Assert.*;

public class UserRepositoryIntegrationTest extends IntegrationTest {
	@Autowired
	UserRepository userRepository;

    String name = "Rambo";
	String email = "alelaska@wp.pl";
	String hashedPassword = "897y6euivbh";

	@Test
	public void shouldSaveAndLoadUser() {
        //given
        User user = new User().withName(name).withEmail(email).withPassword(hashedPassword);

        //when
		userRepository.save(user);

        //then
		User loadedUser = userRepository.load(user.name());
		assertNotNull(loadedUser);
        assertEquals(name, loadedUser.name());
		assertEquals(email, loadedUser.email());
		assertEquals(hashedPassword, loadedUser.password());
	}

	@Test
	public void saveShouldAlsoUpdate() {
        //given
		User user = new User().withName(name).withEmail(email).withPassword(hashedPassword);
		userRepository.save(user);
		String oldId = user.name();

        //when
		userRepository.save(user.withPassword("NEW PASSWORD"));

        //then
		String newId = user.name();
		assertTrue(oldId == newId);
		assertEquals("NEW PASSWORD", userRepository.load(newId).password());
	}

	@Test
	public void findByIdShouldReturnNullIfNotFound() {
		User user = userRepository.load("NONEXISTENT ID");
		assertNull(user);
	}
}
