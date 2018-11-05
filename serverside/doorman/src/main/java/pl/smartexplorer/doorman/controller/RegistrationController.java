package pl.smartexplorer.doorman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.doorman.model.CerberAuthDecisionRegistration;
import pl.smartexplorer.doorman.model.Registration;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 19-10-2018
 * */

@Slf4j
@Controller
public class RegistrationController {
    private RestTemplate restTemplate;

    public RegistrationController() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${scribe.hostname}")
    private String scribeHostname;

    @GetMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public void registrationScribe(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8030/registration");
    }

    @GetMapping("/registration/fb")
    @ResponseStatus(HttpStatus.OK)
    public String registrationFb(Authentication authentication, Registration registration, BindingResult bindingResult) {
        return "registration_facebook";
    }

    @PostMapping("/registration/fb")
    @ResponseStatus(HttpStatus.OK)
    public String registrationFbPost(Authentication authentication, @Valid Registration registration, Model model) {
        registration.setAuthenticationType("FACEBOOK");
        registration.setUsername(authentication.getName());

        final String URL = scribeHostname.concat("/registration/processing");
        try {
            restTemplate.postForEntity(URL, registration, CerberAuthDecisionRegistration.class);
        } catch (Exception ex) {
            log.error("Post request execution failed. Probably connection with Scribe is broken");
            return "error_page";
        }

        model.addAttribute("username", authentication.getName());
        return "registration_success";
    }

}
