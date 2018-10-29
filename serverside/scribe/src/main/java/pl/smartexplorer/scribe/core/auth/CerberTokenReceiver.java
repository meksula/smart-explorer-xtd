package pl.smartexplorer.scribe.core.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.scribe.model.dto.CerberAuthDecission;
import pl.smartexplorer.scribe.model.dto.TokenEstablishData;

import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

@Slf4j
@Service
public class CerberTokenReceiver implements TokenReceiver {
    private RestTemplate restTemplate;

    public CerberTokenReceiver() {
        this.restTemplate = new RestTemplate();
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String updateToken(HttpServletResponse httpServletResponse, TokenEstablishData tokenEstablishData) {
        ResponseEntity<CerberAuthDecission> responseEntity =
                restTemplate.postForEntity("http://localhost:8040/api/v2/token/update", tokenEstablishData, CerberAuthDecission.class);

        CerberAuthDecission cerberAuthDecission = responseEntity.getBody();

        if (cerberAuthDecission == null) {
            String msg = "Something was wrong. CerberAuthDecission is null - some error occured between Scribe and Cerber connection.";
            log.info(msg);
            return msg;
        } else {
            httpServletResponse.addHeader("sev2token", cerberAuthDecission.getSev2token());
            if (cerberAuthDecission.isDecision())
                log.info("Authentication process was successful.");
            else
                log.error("Authentication failed from unrecognized reasons.");

            return cerberAuthDecission.getMessage();
        }

    }

}
