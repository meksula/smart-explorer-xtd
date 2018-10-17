package pl.smartexplorer.sev2Token;

import pl.smartexplorer.sev2Token.core.Sev2TokenManager;
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

public class BasicTokenFacade implements TokenFacade {
    private Sev2TokenData sev2TokenData;
    private TokenGenerator tokenGenerator;
    private TokenMatcher tokenMatcher;
    private Sev2TokenManager tokenManager;

    public BasicTokenFacade(final Sev2TokenType sev2TokenType, final long expirableTimeInMinutes,
                            final DatabasesAvailable databasesAvailable,
                            final String databaseAddress, final String username, final String password) {
        TokenComponentsFactory factory = new TokenComponentsFactoryImpl(sev2TokenType);
        this.tokenGenerator = factory.getTokenGenerator();
        this.tokenMatcher = factory.getTokenMatcher(expirableTimeInMinutes);
        this.sev2TokenData = factory.getSev2TokenData(databasesAvailable, sev2TokenType,
                databaseAddress, username, password);
        this.tokenManager = factory.getSev2TokenManager();
    }

    public BasicTokenFacade(final BasicTokenFacadeBuilder builder) {
        TokenComponentsFactory factory = new TokenComponentsFactoryImpl(builder.sev2TokenType);
        this.tokenGenerator = factory.getTokenGenerator();
        this.tokenMatcher = factory.getTokenMatcher(builder.minutes);
        this.sev2TokenData = factory.getSev2TokenData(builder.databasesAvailable, builder.sev2TokenType,
                builder.databaseAddress, builder.username, builder.password);
        this.tokenManager = factory.getSev2TokenManager();
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

    @Override
    public boolean isExpired(String userId) {
        return tokenManager.isExpired(userId);
    }

    class BasicTokenFacadeBuilder {
        Sev2TokenType sev2TokenType;
        long minutes;
        DatabasesAvailable databasesAvailable;
        String databaseAddress;
        String username;
        String password;

        public BasicTokenFacadeBuilder sev2TokenType(Sev2TokenType sev2TokenType) {
            this.sev2TokenType = sev2TokenType;
            return this;
        }

        public BasicTokenFacadeBuilder expirableTimeInMinutes(long minutes) {
            this.minutes = minutes;
            return this;
        }

        public BasicTokenFacadeBuilder databaseAvailable(DatabasesAvailable databasesAvailable) {
            this.databasesAvailable = databasesAvailable;
            return this;
        }

        public BasicTokenFacadeBuilder databaseAddress(String databaseAddress) {
            this.databaseAddress = databaseAddress;
            return this;
        }

        public BasicTokenFacadeBuilder dbUsername(String username) {
            this.username = username;
            return this;
        }

        public BasicTokenFacadeBuilder dbPassword(String password) {
            this.password = password;
            return this;
        }

        public BasicTokenFacade build() {
            if (sev2TokenType == null || databasesAvailable == null
                    || databaseAddress == null || username == null || password == null) {
                throw new Sev2TokenException("One or more of required properties are null." +
                        "\nYou have to set: Sev2TokenType, expirableTimeInMinutes," +
                        "\nDatabasesAvailable, databaseAddress, username, password");
            }

            return new BasicTokenFacade(this);
        }

    }

}
