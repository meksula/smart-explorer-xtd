package pl.smartexplorer.scribe.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.exception.ScribeException;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Service
public class CustomUserDetailsService implements ExtendedUserDetailsService {
    private CustomRestTemplate restTemplate;
    private static final String MSG = "Authorization failed. Bad credentials or user not exist.";

    public CustomUserDetailsService(CustomRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return restTemplate.requestForUser(username)
                .orElseThrow(() -> new ScribeException(MSG));
    }

    @Override
    public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
        return restTemplate.requestForUser(username, password).orElseThrow(() -> new ScribeException(MSG));
    }

}
