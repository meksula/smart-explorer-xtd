package pl.smartexplorer.cerber.security.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@Slf4j
@Service
public class UserRequestGatewayImpl implements UserRequestGateway {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserRequestGatewayImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        log.info("Password encoder class: " + passwordEncoder.getClass());
    }

    @Override
    public User allowReturnUserEntity(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        boolean usernameEquals = user.getUsername().equals(username);
        boolean passwordsEquals = passwordEncoder.matches(password, user.getEncryptedPassword());

        if (usernameEquals && passwordsEquals) {
            return user;
        }

        return null;
    }

}
