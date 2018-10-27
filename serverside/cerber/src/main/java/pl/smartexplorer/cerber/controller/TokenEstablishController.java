package pl.smartexplorer.cerber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.security.TokenManager;

import javax.servlet.http.HttpServletResponse;

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
    public CerberAuthDecission establishNewToken(@RequestBody TokenEstablishData establishData, HttpServletResponse response) {
        CerberAuthDecission cerberAuthDecission = tokenManager.generateTokenAndSave(establishData);
        if (cerberAuthDecission.getSev2token() == null)
            response.setStatus(409);
        else
            response.setStatus(201);

        return cerberAuthDecission;
    }

    /**
     * This endpoint is enable only for registered users.
     * */
    @PostMapping("/update")
    public CerberAuthDecission updateToken(@RequestBody TokenEstablishData establishData, HttpServletResponse response) throws JsonProcessingException {
        CerberAuthDecission cerberAuthDecission = tokenManager.updateToken(establishData);
        if (cerberAuthDecission.getSev2token() == null)
            response.setStatus(409);
        else
            response.setStatus(200);

        return cerberAuthDecission;
    }

}
