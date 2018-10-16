package pl.smartexplorer.sev2Token;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.core.data.Sev2TokenData;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.exception.Sev2TokenException;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

/**
 * @author
 * Karol Meksuła
 * 16-10-2018
 *
 * Basic, simple implementation of TokenFacade interface.
 * */

//TODO zrobić buildera, bo dużo argumentów
public class BasicTokenFacade implements TokenFacade {
    private Sev2TokenData sev2TokenData;
    private TokenGenerator tokenGenerator;
    private TokenMatcher tokenMatcher;
    private Sev2TokenType sev2TokenType;

    public BasicTokenFacade(final Sev2TokenType sev2TokenType, final long expirableTimeInMinutes,
                            final DatabasesAvailable databasesAvailable,
                            final String databaseAddress, final String username, final String password) {
        TokenComponentsFactory factory = new TokenComponentsFactoryImpl(sev2TokenType);
        this.tokenGenerator = factory.getTokenGenerator();
        this.tokenMatcher = factory.getTokenMatcher(expirableTimeInMinutes);
        this.sev2TokenData = factory.getSev2TokenData(databasesAvailable, sev2TokenType,
                databaseAddress, username, password);
        this.sev2TokenType = sev2TokenType;
    }

    @Override
    public String generateAndSaveToken(String userId, String username, String ipAddress) {
        AbstractSev2Token token = tokenGenerator.generateToken(userId, username, ipAddress);
        sev2TokenData.save(token);
        return tokenGenerator.encodeToken(token);
    }

    @Override
    public boolean allowAccess(String userId, String encryptedToken) {
        AbstractSev2Token optionalAbstractSev2Token = sev2TokenData.fetchByUserId(userId)
                .orElseThrow(() -> new Sev2TokenException("Cannot find user with id: " + userId));
        return tokenMatcher.allowAccess(encryptedToken, optionalAbstractSev2Token);
    }

}
