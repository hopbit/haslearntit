package it.haslearnt.gravatar;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class ConvertsEmailToGravatarUrlTest {

	@Test
	public void convert() {
		ConvertsEmailToGravatarUrl convertsEmailToGravatarUrl = new ConvertsEmailToGravatarUrl();

		String url = convertsEmailToGravatarUrl.convert("zenon@wp.pl");

		assertThat(url).isEqualTo("http://gravatar.com/avatar/f382c59da95475db2a26f38c57d6c978");
	}
}
