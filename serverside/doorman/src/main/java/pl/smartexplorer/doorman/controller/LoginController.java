package pl.smartexplorer.doorman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.doorman.model.TokenEstablishData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 19-10-2018
 * */

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    private RestTemplate restTemplate;

    public LoginController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/option")
    @ResponseStatus(HttpStatus.OK)
    public String login() {
        log.info("Login option page.");
        return "login";
    }

    @GetMapping("/facebook")
    @ResponseStatus(HttpStatus.OK)
    public void loginFacebook(HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws IOException {

        if (authentication.isAuthenticated()) {
            String[] tokenParams = authTokenRequest(authentication, request);

            if (tokenParams[2].contains(":8030")) {
                final String REGISTRATION = tokenParams[2];
                log.info("User try to login, but has not account. Transferring to " + tokenParams[2]);
                response.sendRedirect(REGISTRATION);
            } else {
                response.addHeader(tokenParams[0], tokenParams[1]);
                log.info("Builder header: " + tokenParams[0] + ", " + tokenParams[1]);
                log.info("Logged successfully by facebook oAuth 2.0");
                response.sendRedirect("http://localhost:8080");
            }
        } else {
            log.info("oAuth authentication failed.");
            response.sendRedirect("http://localhost:8010/login/error");
        }
    }

    @GetMapping("/scribe")
    @ResponseStatus(HttpStatus.OK)
    public void loginScribe(HttpServletResponse response) throws IOException {
        log.debug("Redirect to Scribe");
        response.sendRedirect("http://localhost:8030/login");
    }

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String loginError() {
        return "login_error";
    }

    private String[] authTokenRequest(Authentication authentication, HttpServletRequest request) {
        TokenEstablishData tokenEstablishData = createTokenEstablishData(authentication, request);
        ResponseEntity<String> authEntity = restTemplate.postForEntity("http://localhost:8030/api/v2/auth", tokenEstablishData, String.class);
        log.info("Response authorization from Scribe: [" + authEntity.getBody() + "]");
        log.info("Received sev2token: " + String.valueOf(authEntity.getHeaders().get("sev2token")));
        return new String[] {"sev2token", String.valueOf(authEntity.getHeaders().get("sev2token")), authEntity.getBody()};
    }

    private TokenEstablishData createTokenEstablishData(Authentication authentication, HttpServletRequest request) {
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        return new TokenEstablishData(null, username, ipAddress);
    }

}
