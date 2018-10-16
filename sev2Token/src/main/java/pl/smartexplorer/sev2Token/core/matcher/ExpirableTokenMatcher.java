package pl.smartexplorer.sev2Token.core.matcher;

import pl.smartexplorer.sev2Token.exception.Sev2TokenException;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

/**
 * @author
 * Karol Meksuła
 * 13-10-2018
 * */

public class ExpirableTokenMatcher implements TokenMatcher {
    /**
     * @param(TOKEN_EXPIRATION_TIME define token expiration time in MINUTES)
     * */
    private long tokenExpirationTime;

    public ExpirableTokenMatcher(long expirationTime) {
        this.tokenExpirationTime = expirationTime;
    }

    @Override
    public boolean isTokenExpired(String encryptedToken) {
        String decoded = new String(Base64.getDecoder().decode(encryptedToken), Charset.forName("UTF-8"));
        LocalDateTime tokenReleaseDate = extractDateFromToken(decoded);
        long minutesElapsed = ChronoUnit.MINUTES.between(tokenReleaseDate, LocalDateTime.now());

        return minutesElapsed > tokenExpirationTime;
    }

    /**
     * @param encryptedTokenSent is post in request Base64 encoded
     * @param dbToken is Base64 encoded token fetched from database to verify request
     * */
    @Override
    public boolean allowAccess(String encryptedTokenSent, String dbToken) {
        return encryptedTokenSent.equals(dbToken);
    }

    /**
     * The method compares the entity from the database with the sent token.
     */
    @Override
    public boolean allowAccess(String encryptedTokenSent, AbstractSev2Token abstractSev2Token) {
        Sev2TokenExpirable token = (Sev2TokenExpirable) abstractSev2Token;
        Sev2TokenExpirable extractedToken = buildToken(extractParametersFromToken(encryptedTokenSent));
        return token.equals(extractedToken)
                && !token.isExpired();
    }

    /**
     * Method accept only DECRYPTED SEV2Token
     * */
    private LocalDateTime extractDateFromToken(String decodedToken) {
        String[] datePiece = decodedToken.split("\\+");
        try {
            return LocalDateTime.parse(datePiece[4]);
        } catch (Exception exception) {
            throw new Sev2TokenException("Invalid token: cannot extract date value.");
        }

    }

    /**
     * Method accept only DECRYPTED SEV2Token
     * */
    String[] extractParametersFromToken(String encryptedTokenSent) {
        String decoded = new String(Base64.getDecoder().decode(encryptedTokenSent), Charset.forName("UTF-8"));
        String[] params = decoded.split("\\+");

        params[0] = params[0].substring(1);
        params[6] = params[6].substring(0, params[6].length() - 1);

        return params;
    }

    private Sev2TokenExpirable buildToken(String[] params) {
        Sev2TokenExpirable tokenExpirable = new Sev2TokenExpirable(params[0], params[1]);
        tokenExpirable.setTokenType(Sev2TokenType.valueOf(params[2]));
        tokenExpirable.setExpired(Boolean.parseBoolean(params[3]));
        tokenExpirable.setDate(LocalDateTime.parse(params[4]));
        tokenExpirable.setSev2Uiid(UUID.fromString(params[5]));
        tokenExpirable.setIpAddress(params[6]);
        return tokenExpirable;
    }

}
