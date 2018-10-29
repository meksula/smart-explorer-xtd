package pl.smartexplorer.scribe.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

public interface ExtendedUserDetailsService extends UserDetailsService {
    UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException;
}
