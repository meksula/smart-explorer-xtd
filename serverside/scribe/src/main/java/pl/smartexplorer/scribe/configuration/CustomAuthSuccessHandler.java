package pl.smartexplorer.scribe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.smartexplorer.scribe.core.auth.TokenReceiver;
import pl.smartexplorer.scribe.model.dto.TokenEstablishData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

@Slf4j
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    private TokenReceiver tokenReceiver;

    public CustomAuthSuccessHandler(TokenReceiver tokenReceiver) {
        this.tokenReceiver = tokenReceiver;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        TokenEstablishData token = new TokenEstablishData();
        token.setUserId(String.valueOf(authentication.getPrincipal()));
        token.setUsername(authentication.getName());
        token.setIpAddress(request.getRemoteAddr());

        tokenReceiver.updateToken(response, token);
    }

}
