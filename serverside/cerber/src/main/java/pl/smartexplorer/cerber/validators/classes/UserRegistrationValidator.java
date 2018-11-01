package pl.smartexplorer.cerber.validators.classes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.repository.UserRepository;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Slf4j
@Component
@AllArgsConstructor
public class UserRegistrationValidator {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public boolean isAbleToRegister(final User user) {
        if (userRepository == null || tokenRepository == null) {
            log.error("UserRepository or TokenRepository not injected correctly because they are null.");
            return false;
        }

        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            log.error("User is null. Not initialized.");
            return false;
        }

        boolean userExist = userRepository.findByUsername(user.getUsername()).isPresent();
        boolean tokenExist = tokenRepository.findByUsername(user.getUsername()).isPresent();

        return !userExist && !tokenExist;
    }

}
