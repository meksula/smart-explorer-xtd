package pl.smartexplorer.doorman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author
 * Karol Meksuła
 * 19-10-2018
 * */

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/option")
    @ResponseStatus(HttpStatus.OK)
    public String login() {
        log.info("Login option page.");
        return "login";
    }

    @GetMapping("/facebook")
    @ResponseStatus(HttpStatus.OK)
    public String loginFacebook(HttpServletResponse response, Principal principal) throws IOException {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;

        if (authentication.isAuthenticated()) {
            log.info("Logged successfully by facebook oAuth 2.0");
            response.sendRedirect("http://localhost:8080");
        }

        return "login_error";
    }

    @GetMapping("/scribe")
    @ResponseStatus(HttpStatus.OK)
    public void loginScribe(HttpServletResponse response) throws IOException {
        log.debug("Redirect to Scribe");
        response.sendRedirect("http://localhost:8030/login");
    }

}
