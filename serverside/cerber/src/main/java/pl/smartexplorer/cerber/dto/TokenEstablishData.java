package pl.smartexplorer.cerber.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TokenEstablishData {
    private String userId;
    private String username;
    private String ipAddress;

    @Override
    public String toString() {
        return "User " + username + " try to establish new token from IP: " + ipAddress + "\nuserId : " + userId;
    }

}
