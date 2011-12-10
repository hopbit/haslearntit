package it.haslearnt.gravatar;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ConvertsEmailToGravatarUrl {

	private static final String URS_PREFIX = "http://gravatar.com/avatar/";

	public String convert(String email) {
		PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
		return URS_PREFIX + passwordEncoder.encodePassword(email, null);
	}

}
