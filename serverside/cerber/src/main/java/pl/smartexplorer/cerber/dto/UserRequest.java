package pl.smartexplorer.cerber.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserRequest {
    private String username;
    private String password;
}
