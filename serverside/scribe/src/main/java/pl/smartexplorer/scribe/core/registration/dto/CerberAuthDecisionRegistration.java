package pl.smartexplorer.scribe.core.registration.dto;

import lombok.Getter;
import lombok.Setter;
import pl.smartexplorer.scribe.model.dto.CerberAuthDecission;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Getter
@Setter
public class CerberAuthDecisionRegistration extends CerberAuthDecission {
    private String verificationUuid;
    private String verificationLink;
}
