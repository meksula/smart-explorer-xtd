package pl.smartexplorer.scribe.core.registration.builders;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.smartexplorer.scribe.core.registration.dto.Registration;
import pl.smartexplorer.scribe.model.User;

import java.time.LocalDateTime;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

public class PrimaryRegistrationBuilder implements RegistrationBuilder<User, Registration> {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User mapToCerberRequiredObject(Registration argument) {
        User user = new User();
        user.setAuthenticationType(argument.getAuthenticationType());
        user.setSocialServiceId(argument.getSocialServiceId());
        user.setSocialUsername(argument.getSocialUsername());
        user.setUsername(argument.getUsername());
        user.setEncryptedPassword(passwordEncoder.encode(argument.getPassword()));
        user.setEmail(argument.getEmail());
        user.setJoinDate(LocalDateTime.now().toString());
        user.setAccountNonExpired(false);
        user.setAccountNonLocked(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(false);
        return user;
    }

}
