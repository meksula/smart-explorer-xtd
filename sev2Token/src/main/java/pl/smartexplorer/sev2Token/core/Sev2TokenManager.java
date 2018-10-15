package pl.smartexplorer.sev2Token.core;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public interface Sev2TokenManager {
    String generate(String userId, String username, String ipAddress);

    boolean isExpired(String userId);

    boolean allowAccess(String encryptedToken, String encodedTokenFromDatabase);
}
