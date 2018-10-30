package pl.smartexplorer.cerber.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.smartexplorer.cerber.model.user.User;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CerberAuthDecission {
    private boolean decision;
    private String sev2token;
    private String message;
    private User user;
}
