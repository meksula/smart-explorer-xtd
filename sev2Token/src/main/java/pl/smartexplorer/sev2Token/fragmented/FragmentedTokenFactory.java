package pl.smartexplorer.sev2Token.fragmented;

import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.ExpirableTokenMatcher;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.exception.Sev2TokenException;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public class FragmentedTokenFactory {
    private Sev2TokenType tokenType;
    private final static String MESSAGE = "Enum type not enable.";

    public FragmentedTokenFactory(Sev2TokenType sev2TokenType) {
        this.tokenType = sev2TokenType;
    }

    public TokenGenerator createTokenGenerator() {
        if (tokenType == Sev2TokenType.EXPIRABLE) {
            return new ExpirableTokenGenerator();
        }
        else {
            throw new Sev2TokenException(MESSAGE);
        }
    }

    public TokenMatcher createTokenMatcher(long tokenExpireTimeInMinutes) {
        if (tokenType == Sev2TokenType.EXPIRABLE) {
            return new ExpirableTokenMatcher(tokenExpireTimeInMinutes);
        }
        else {
            throw new Sev2TokenException(MESSAGE);
        }
    }

}
