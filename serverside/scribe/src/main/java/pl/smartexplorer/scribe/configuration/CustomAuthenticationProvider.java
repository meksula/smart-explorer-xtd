package pl.smartexplorer.scribe.configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import pl.smartexplorer.scribe.model.User;
import pl.smartexplorer.scribe.model.dto.CerberAuthDecission;

import java.util.Collection;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

@Slf4j
@Primary
@Component
@Configuration
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CerberAuthDecission cerberAuthDecission = (CerberAuthDecission) userDetailsService.loadUserByUsernameAndPassword(authentication.getName(),
                authentication.getCredentials().toString());

        if (cerberAuthDecission.getUser() == null) {
            log.info("Some inner error occured. User is null.");
            return null;
        }

        User user = cerberAuthDecission.getUser();
        log.info("Authentication successful.");

        return new CustomAuthentication() {
            @Override
            public String getSev2token() {
                return cerberAuthDecission.getSev2token();
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return user.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return user.getEncryptedPassword();
            }

            @Override
            public Object getDetails() {
                return user.getAuthenticationType();
            }

            @Override
            public Object getPrincipal() {
                return user.getUserId();
            }

            @Override
            public boolean isAuthenticated() {
                return user != null;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        };
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
