package pl.smartexplorer.sev2Token.core;

import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.ExpirableTokenMatcher;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public class Sev2TokenExpirableManager implements Sev2TokenManager {
    private TokenGenerator tokenGenerator;
    private TokenMatcher tokenMatcher;

    public Sev2TokenExpirableManager() {
        this.tokenGenerator = new ExpirableTokenGenerator();
        this.tokenMatcher = new ExpirableTokenMatcher(120);
    }

    @Override
    public String generate(String userId, String username) {
        AbstractSev2Token token = tokenGenerator.generateToken(userId, username);
        return tokenGenerator.encodeToken(token);
    }

    @Override
    public boolean isExpired(String encryptedToken) {
        return tokenMatcher.isTokenExpired(encryptedToken);
    }

    @Override
    public boolean allowAccess(String encryptedToken, String encodedTokenDatabase) {
        return tokenMatcher.allowAccess(encryptedToken, encodedTokenDatabase);
    }

}
