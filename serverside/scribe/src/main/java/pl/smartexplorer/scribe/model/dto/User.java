package pl.smartexplorer.scribe.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;

import java.util.*;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User implements UserDetails {

    private long userId;
    private AuthenticationType authenticationType;
    private long userProfileId;
    private String socialServiceId;
    private String socialUsername;
    private String username;
    private String encryptedPassword;
    private String email;
    private String joinDate;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

    @Override
    public String getPassword() {
        return this.encryptedPassword;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}
