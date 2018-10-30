package pl.smartexplorer.scribe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

@Slf4j
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${webclient.hostname}")
    private String clientHost;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
        response.addHeader("sev2token", customAuthentication.getSev2token());
        try {
            response.sendRedirect(clientHost);
        } catch (IOException e) {
            log.error("Cannot redirect to " + clientHost + ", some error occured at time of execute `response.sendRedirect(..)`");
        }
    }

}
