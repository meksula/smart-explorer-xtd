package pl.smartexplorer.sev2Token;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.core.data.Sev2TokenData;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

/**
 * @author
 * Karol Meksuła
 * 16-10-2018
 * */

public interface TokenComponentsFactory {
    Sev2TokenData getSev2TokenData(final DatabasesAvailable databasesAvailable, final Sev2TokenType tokenType,
                                   final String databaseAddress, final String username, final String password);

    TokenGenerator getTokenGenerator();

    TokenMatcher getTokenMatcher(long expireTimeInMinutes);
}
