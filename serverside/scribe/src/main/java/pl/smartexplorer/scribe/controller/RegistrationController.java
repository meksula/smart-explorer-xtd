package pl.smartexplorer.scribe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.smartexplorer.scribe.core.registration.dto.Registration;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Slf4j
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String registrationPage(Registration registration) {
        return "registration";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String registrationFormPost(@Valid Registration registration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.info("ERROR: " + objectError.toString()));
            return "registration";
        }

        return "registration_success";
    }

    @GetMapping("/fb")
    @ResponseStatus(HttpStatus.OK)
    public void registrationByFacebook(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8010/registration/fb");
    }

}
