package pl.smartexplorer.scribe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.exception.ScribeAuthenticationException;
import pl.smartexplorer.scribe.model.dto.CerberAuthDecission;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Service
@Slf4j
public class CustomUserDetailsService implements ExtendedUserDetailsService {
    private CustomRestTemplate restTemplate;
    private static final String MSG = "Authorization failed. Bad credentials or user not exist.";

    public CustomUserDetailsService(CustomRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return restTemplate.requestForUser(username)
                .orElseThrow(() -> new ScribeAuthenticationException(MSG));
    }

    @Override
    public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
        return restTemplate.requestForUserAndSev2Token(username, password).orElseGet(() -> {
            log.error("Attempt to fetch user from Cerber went wrong. Probably user not exist or it's error with connection between Scribe and Cerber.");
            return new CerberAuthDecission();
        });
    }

}
