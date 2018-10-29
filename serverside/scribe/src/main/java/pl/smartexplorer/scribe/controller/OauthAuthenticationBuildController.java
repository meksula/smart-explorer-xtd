package pl.smartexplorer.scribe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.scribe.core.auth.TokenReceiver;
import pl.smartexplorer.scribe.model.dto.TokenEstablishData;

import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Slf4j
@RestController("/api/v2/auth")
public class OauthAuthenticationBuildController {
    private TokenReceiver tokenReceiver;

    public OauthAuthenticationBuildController(TokenReceiver tokenReceiver) {
        this.tokenReceiver = tokenReceiver;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String generateTokenForUser(HttpServletResponse response, @RequestBody TokenEstablishData token) {
        return tokenReceiver.updateToken(response, token);
    }

}
