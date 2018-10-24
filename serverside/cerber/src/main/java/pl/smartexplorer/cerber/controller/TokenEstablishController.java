package pl.smartexplorer.cerber.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/establish")
    @ResponseStatus(HttpStatus.OK)
    public String establishNewToken(@RequestBody TokenEstablishData establishData) {
        return tokenManager.generateTokenAndSave(establishData);
    }

    /*@PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public String establishNewToken(@RequestBody TokenEstablishData establishData) {
        return tokenManager.generateTokenAndSave(establishData);
    }*/

}
