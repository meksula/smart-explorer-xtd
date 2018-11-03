package pl.smartexplorer.scribe.core.registration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.scribe.core.registration.dto.CerberAuthDecisionRegistration;
import pl.smartexplorer.scribe.core.registration.dto.Registration;
import pl.smartexplorer.scribe.core.registration.service.RegistrationProcessExecutor;
import pl.smartexplorer.scribe.core.registration.validator.PasswordValidator;

import javax.validation.Valid;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 *
 * This is a main and unique controller of registration Scribe module.
 * Here are defined endpoints to receive and further process registration object.
 * Mainly use for receive registration request from Doorman and oAuth2.0
 * */

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/registration/processing")
public class RegistrationProcessingController {
    private RegistrationProcessExecutor<CerberAuthDecisionRegistration, Registration> registrationProcessExecutor;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CerberAuthDecisionRegistration buildAndSendToDatabase(@Valid @RequestBody Registration registration) {
        if (!PasswordValidator.validPassword(registration.getPassword(), registration.getPasswordRepeated())) {
            CerberAuthDecisionRegistration cadr = new CerberAuthDecisionRegistration();
            cadr.setMessage("Hasła nie są identyczne.");
            log.info("The both password are not identical.");
        }

        return registrationProcessExecutor.execute(registration);
    }

}
