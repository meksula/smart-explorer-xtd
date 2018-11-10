package pl.smartexplorer.scribe.services.mailer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ExternalMailPayloadWrapper implements Serializable {
    private String templateName;
    private MailTarget mailTarget;
    private Map<String, String> properties;
}
