package pl.smartexplorer.sev2Token.core;

import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

public class Sev2TokenExpirableManager implements Sev2TokenManager {
    private TokenGenerator tokenGenerator;

    public Sev2TokenExpirableManager() {
        this.tokenGenerator = new ExpirableTokenGenerator();
    }

    @Override
    public String generate(String userId, String username) {
        AbstractSev2Token token = tokenGenerator.generateToken(userId, username);
        return tokenGenerator.encodeToken(token);
    }

    @Override
    public boolean isExpired(String userId) {
        return false;
    }

    @Override
    public boolean isMatch(String userId, AbstractSev2Token token) {
        return false;
    }

    @Override
    public boolean allowAccess(AbstractSev2Token token) {
        return false;
    }
}
