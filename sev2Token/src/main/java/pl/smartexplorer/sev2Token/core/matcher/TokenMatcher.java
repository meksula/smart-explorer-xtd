package pl.smartexplorer.sev2Token.core.matcher;

/**
 * @author
 * Karol Meksuła
 * 13-10-2018
 * */

public interface TokenMatcher {
    boolean isTokenExpired(String encryptedToken);

    boolean allowAccess(String encryptedTokenPost, String tokenFromDatabase);
}
