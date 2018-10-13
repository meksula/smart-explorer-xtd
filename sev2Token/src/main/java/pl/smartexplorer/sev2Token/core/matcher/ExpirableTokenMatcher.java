package pl.smartexplorer.sev2Token.core.matcher;

import pl.smartexplorer.sev2Token.exception.Sev2TokenException;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

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
     * @param encryptedTokenPost is post in request Base64 encoded
     * @param dbToken is Base64 encoded token fetched from database to verify request
     * */
    @Override
    public boolean allowAccess(String encryptedTokenPost, String dbToken) {
        return encryptedTokenPost.equals(dbToken);
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

}
