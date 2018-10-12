package pl.smartexplorer.sev2Token.core;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public interface Sev2TokenManager {
    String generate(String userId, String username);

    boolean isExpired(String userId);

    boolean isMatch(String userId, AbstractSev2Token token);

    boolean allowAccess(AbstractSev2Token token);
}
