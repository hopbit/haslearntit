package setup;

import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import predefinedData.UserPredefinedData;

@Component
public class FakeAuthenticationService {
	@Resource(name = "authenticationManager")
	AuthenticationManager authenticationManager;

	@Resource(name = "userRepository")
	private UserRepository userRepository;

	protected void authenticateTestUser(IntegrationTest integrationTest) {
		authenticateTestUser();
	}

	public void authenticateTestUser() {
		saveUserInDatabase();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				UserPredefinedData.name, UserPredefinedData.password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public void saveUserInDatabase() {
		User user = new User().withName(UserPredefinedData.name).withEmail(UserPredefinedData.email)
				.withPassword(UserPredefinedData.hashedPassword);
		userRepository.save(user);
	}

}
