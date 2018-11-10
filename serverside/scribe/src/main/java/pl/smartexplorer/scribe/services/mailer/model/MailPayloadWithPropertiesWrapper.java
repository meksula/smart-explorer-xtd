package pl.smartexplorer.scribe.services.mailer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 * */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailPayloadWithPropertiesWrapper extends MailPayloadWrapper {
    private Map<String, String> properties;
}
