package pl.smartexplorer.sev2Token;

/**
 * @author
 * Karol Meksuła
 * 16-10-2018
 *
 * Main, entry interface of sev2Token module.
 * Use it to build sev2 token auth strategy in your application.
 * */
public interface TokenFacade {
    String generateAndSaveToken(String userId, String username, String ipAddress);

    boolean allowAccess(String userId, String encryptedToken);

    boolean isExpired(String userId);
}
