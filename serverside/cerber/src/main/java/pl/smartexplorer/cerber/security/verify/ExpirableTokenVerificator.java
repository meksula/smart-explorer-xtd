package pl.smartexplorer.cerber.security.verify;

/**
 * @author
 * Karol Meksuła
 * 28-10-2018
 * */

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.sev2Token.core.matcher.ExpirableTokenMatcher;
import pl.smartexplorer.sev2Token.core.matcher.RetrieveTokenData;
import pl.smartexplorer.sev2Token.core.matcher.TokenMatcher;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import javax.servlet.http.HttpServletRequest;

/**
 * Each @Service that return data from database should extends this abstract class
 * */
@Slf4j
public abstract class ExpirableTokenVerificator {
    public final String TOKEN_NAME = "sev2token";
    public final long TOKEN_EXPIRE_TIME = 180;
    private RetrieveTokenData retrieveTokenData;
    private TokenMatcher tokenMatcher;
    private TokenRepository tokenRepository;

    public ExpirableTokenVerificator(TokenRepository tokenRepository) {
        this.retrieveTokenData = new RetrieveTokenData();
        this.tokenMatcher = new ExpirableTokenMatcher(TOKEN_EXPIRE_TIME);
        this.tokenRepository = tokenRepository;
    }

    public boolean verifyToken(HttpServletRequest request) {
        String userId = extractUserId(request);
        if (userId == null) return false;

        Sev2TokenExpirable tokenExpirable = (Sev2TokenExpirable) tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new SmartExplorerRepositoryException("Error occured. Cannot find user with userId: " + userId + ". " +
                        "Something was wrong. Check database or user who is not exist has token."));

        log.info(userId + " authenticated successfully.");
        return tokenMatcher.allowAccess(extractToken(request), tokenExpirable);
    }

    private String extractUserId(HttpServletRequest request) {
        String token = extractToken(request);
        try {
            return retrieveTokenData.extractParameter(token, RetrieveTokenData.TokenParams.USER_ID);
        } catch (IllegalArgumentException ex) {
            log.error("Invalid sev2token came from " + request.getRemoteAddr());
            return null;
        }
    }

    private String extractToken(HttpServletRequest request) {
        return request.getHeader(TOKEN_NAME);
    }

}
