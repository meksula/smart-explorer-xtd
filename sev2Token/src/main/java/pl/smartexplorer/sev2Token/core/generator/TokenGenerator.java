package pl.smartexplorer.sev2Token.core.generator;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public interface TokenGenerator {
    AbstractSev2Token generateToken(String userId, String username);

    String encodeToken(AbstractSev2Token token);
}
