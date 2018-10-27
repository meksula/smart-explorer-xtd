package pl.smartexplorer.cerber.model.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.*;

/**
 * @author
 * Karol Meksuła
 * 26-10-2018
 * */

@Data
@Entity
@Table(name = "user_profile")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userProfileId;
    private String username;

}
