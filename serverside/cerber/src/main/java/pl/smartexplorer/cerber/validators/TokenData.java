package pl.smartexplorer.cerber.validators;

import pl.smartexplorer.cerber.validators.classes.TokenDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Documented
@Constraint(validatedBy = TokenDataValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenData {

    String message()
            default "Cannot generate token from this data";

    Class<?>[] groups()
            default {};

    Class<? extends Payload>[] payload()
            default {};
}
