package pl.smartexplorer.scribe.services.mailer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.io.Serializable;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MailTarget implements Serializable {
    private String targetEmail;
    private String username;
    private String mailTitle;
    private String firstName;
    private String lastName;
    private String preparedDate;
}
