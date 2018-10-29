package pl.smartexplorer.scribe.core.auth;

import pl.smartexplorer.scribe.model.dto.TokenEstablishData;

import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * Karol Meksuła
 * 29-10-2018
 * */

public interface TokenReceiver {
   // String createToken(); //TODO method should be implemented when registration module will be written

    String updateToken(HttpServletResponse httpServletResponse, TokenEstablishData tokenEstablishData);
}
