package pl.smartexplorer.cerber.model.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Data
@Entity
@Table(name = "users")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    @Id
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
