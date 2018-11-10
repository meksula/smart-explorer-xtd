package pl.smartexplorer.cerber.dto;

import lombok.Getter;
import lombok.Setter;

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
