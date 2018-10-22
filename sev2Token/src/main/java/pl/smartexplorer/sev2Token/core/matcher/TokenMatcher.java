package pl.smartexplorer.sev2Token.core.matcher;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 13-10-2018
 * */

public interface TokenMatcher {
    boolean isTokenExpired(String encryptedToken);

    boolean isTokenExpired(AbstractSev2Token abstractSev2Token);

    boolean allowAccess(String encryptedTokenPost, String tokenFromDatabase);

    boolean allowAccess(String encryptedTokenPost, AbstractSev2Token abstractSev2Token);

}
