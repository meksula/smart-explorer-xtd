package pl.smartexplorer.cerber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.security.TokenManager;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@RestController
@RequestMapping("/api/v2/token")
public class TokenEstablishController {
    private TokenManager tokenManager;

    public TokenEstablishController(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * This endpoint is enable for new users that wants to create account.
     * */
    @PostMapping("/establish")
    public CerberAuthDecission establishNewToken(@RequestBody TokenEstablishData establishData) {
        return tokenManager.generateTokenAndSave(establishData);
    }

    /**
     * This endpoint is enable only for registered users.
     * */
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public CerberAuthDecission updateToken(@RequestBody TokenEstablishData establishData) throws JsonProcessingException {
        return tokenManager.updateToken(establishData);
    }

}
