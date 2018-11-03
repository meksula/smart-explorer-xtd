package pl.smartexplorer.scribe.services.mailer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MailTarget {
    private String targetEmail;
    private String username;
    private String mailTitle;
    private String firstName;
    private String lastName;
    private String preparedDate;
}
