package pl.smartexplorer.scribe.services.mailer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TemplateEncoded {
    private String templateBase64;

    public TemplateEncoded(String templateBase64) {
        this.templateBase64 = templateBase64;
    }
}
