package pl.smartexplorer.scribe.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.smartexplorer.scribe.core.registration.dto.CerberAuthDecisionRegistration;
import pl.smartexplorer.scribe.core.registration.dto.Registration;
import pl.smartexplorer.scribe.core.registration.service.RegistrationProcessExecutor;
import pl.smartexplorer.scribe.core.registration.validator.PasswordValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 *
 * Controller using for registration by username and password in Scribe.
 * */

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationMvcController {
    private RegistrationProcessExecutor<CerberAuthDecisionRegistration, Registration> registrationProcessExecutor;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String registrationPage(Registration registration) {
        return "registration";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String registrationFormPost(@Valid Registration registration, BindingResult bindingResult, Model model) {
        if (!PasswordValidator.validPassword(registration.getPassword(), registration.getPasswordRepeated())) {
            bindingResult.addError(new ObjectError("password", "Hasła nie są takie same"));
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.info("ERROR: " + objectError.toString()));
            return "registration";
        }

        CerberAuthDecisionRegistration cadr = registrationProcessExecutor.execute(registration);
        model.addAttribute("cadr", cadr);
        return "registration_success";
    }

    @GetMapping("/fb")
    @ResponseStatus(HttpStatus.OK)
    public void registrationByFacebook(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8010/registration/fb");
    }

}
