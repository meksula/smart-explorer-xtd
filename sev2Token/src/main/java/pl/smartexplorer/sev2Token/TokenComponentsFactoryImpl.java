package pl.smartexplorer.sev2Token;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.core.data.Sev2TokenData;
import pl.smartexplorer.sev2Token.core.data.Sev2TokenDataImpl;
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.ExpirableTokenMatcher;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

/**
 * @author
 * Karol Meksuła
 * 16-10-2018
 *
 * Factory able to extend to new token types.
 * */

public class TokenComponentsFactoryImpl implements TokenComponentsFactory {
    private Sev2TokenType sev2TokenType;

    public TokenComponentsFactoryImpl(Sev2TokenType sev2TokenType) {
        this.sev2TokenType = sev2TokenType;
    }

    @Override
    public Sev2TokenData getSev2TokenData(final DatabasesAvailable databasesAvailable, final Sev2TokenType tokenType,
                                          final String databaseAddress, final String username, final String password) {
        if (sev2TokenType == Sev2TokenType.EXPIRABLE) {
            return new Sev2TokenDataImpl(databasesAvailable, tokenType, databaseAddress, username, password);
        } else {
            return null;
        }
    }

    @Override
    public TokenGenerator getTokenGenerator() {
        return new ExpirableTokenGenerator();
    }

    @Override
    public TokenMatcher getTokenMatcher(long expireTimeInMinutes) {
        return new ExpirableTokenMatcher(expireTimeInMinutes);
    }
}
