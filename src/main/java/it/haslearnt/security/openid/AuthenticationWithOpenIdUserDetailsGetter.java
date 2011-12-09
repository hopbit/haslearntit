package it.haslearnt.security.openid;

import it.haslearnt.security.AuthenticationUserDetails;
import it.haslearnt.user.User;
import it.haslearnt.user.UserOpenId;
import it.haslearnt.user.UserOpenIdRepository;
import it.haslearnt.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationWithOpenIdUserDetailsGetter implements UserDetailsService {
    private UserOpenIdRepository userOpenIdRepository;
    private UserRepository userRepository;

    public AuthenticationWithOpenIdUserDetailsGetter(UserOpenIdRepository userOpenIdRepository, UserRepository userRepository) {
        this.userOpenIdRepository = userOpenIdRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String openIdLogin) throws UsernameNotFoundException {
        UserOpenId userOpenId = userOpenIdRepository.load(openIdLogin);
        throwExceptionIfNotFound(userOpenId, openIdLogin);
        User user = userRepository.load(userOpenId.name());
        throwExceptionIfNotFound(user, openIdLogin);
        return new AuthenticationUserDetails(user);
    }

    private void throwExceptionIfNotFound(Object object, String openIdLogin) {
        if (object == null) {
            throw new UsernameNotFoundException("User with open id login " + openIdLogin + "  has not been found.");
        }
    }
}
