package pl.smartexplorer.scribe.services.mailer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TemplateEncoded implements Serializable {
    private String templateBase64;

    public TemplateEncoded(String templateBase64) {
        this.templateBase64 = templateBase64;
    }
}
