package pl.smartexplorer.scribe.services.mailer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailPayloadWrapper implements Serializable {
    private TemplateEncoded templateEncoded;
    private MailTarget mailTarget;
}
