package pl.smartexplorer.cerber.validators.classes;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.validators.TokenData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
public class TokenDataValidator implements ConstraintValidator<TokenData, TokenEstablishData> {

    @Override
    public boolean isValid(TokenEstablishData value, ConstraintValidatorContext context) {
        log.debug("Checking is data valid.");
        return value.getIpAddress().contains(".")
                && value.getUserId() != null
                && value.getUsername() != null;
    }

}
