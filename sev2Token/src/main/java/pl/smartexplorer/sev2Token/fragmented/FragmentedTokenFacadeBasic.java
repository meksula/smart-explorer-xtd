package pl.smartexplorer.sev2Token.fragmented;

import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public class FragmentedTokenFacadeBasic implements FragmentedTokenFacade {
    private TokenGenerator tokenGenerator;
    private TokenMatcher tokenMatcher;

    public FragmentedTokenFacadeBasic(Sev2TokenType tokenType, long tokenExpirableTimeInMinutes) {
        FragmentedTokenFactory factory = new FragmentedTokenFactory(tokenType);
        this.tokenGenerator = factory.createTokenGenerator();
        this.tokenMatcher = factory.createTokenMatcher(tokenExpirableTimeInMinutes);
    }

    @Override
    public AbstractSev2Token generateToken(String userId, String username, String ipAddress) {
        return tokenGenerator.generateToken(userId, username, ipAddress);
    }

    @Override
    public String encodeToken(AbstractSev2Token token) {
        return tokenGenerator.encodeToken(token);
    }

    @Override
    public String generateAndEncode(String userId, String username, String ipAddress) {
        AbstractSev2Token token = generateToken(userId, username, ipAddress);
        return tokenGenerator.encodeToken(token);
    }

    @Override
    public boolean isExpired(AbstractSev2Token token) {
        return tokenMatcher.isTokenExpired(token);
    }

    @Override
    public boolean isExpired(String encoded) {
        return tokenMatcher.isTokenExpired(encoded);
    }

    @Override
    public boolean allow(String encodedFromRequest, AbstractSev2Token tokenFromDatabase) {
        return tokenMatcher.allowAccess(encodedFromRequest, tokenFromDatabase);
    }
}
