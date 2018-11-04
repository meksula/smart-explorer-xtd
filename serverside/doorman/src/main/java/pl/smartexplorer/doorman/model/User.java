package pl.smartexplorer.doorman.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    private String userId;
    private String authenticationType;
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

}
