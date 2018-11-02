package pl.smartexplorer.doorman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/scribe/registration")
    @ResponseStatus(HttpStatus.OK)
    public void registrationScribe(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8030");
    }

    @GetMapping("/registration/fb")
    @ResponseStatus(HttpStatus.OK)
    public String registrationFb(Authentication authentication, Registration registration, BindingResult bindingResult) {
        System.out.println(authentication.getName());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getDetails());

        return "registration_facebook";
    }

    @PostMapping("/registration/fb")
    @ResponseStatus(HttpStatus.OK)
    public String registrationFbPost(Authentication authentication, @Valid Registration registration,
                                     HttpServletResponse response) throws IOException {

        //TODO wysyłanie encji do Scribe, który to zbuduje jsona, zwaliduje i wyśle do Cerbera.
        //TODO Scribe także zajmie się wysłaniem maila

        return "registration_success";
    }

}
