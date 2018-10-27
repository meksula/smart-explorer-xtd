package pl.smartexplorer.cerber.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.repository.id.UserIdGenerator;
import pl.smartexplorer.cerber.validators.TokenData;
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
@Service
public class MainTokenManager implements TokenManager {
    private TokenRepository tokenRepository;
    private TokenGenerator tokenGenerator;
    private UserIdGenerator userIdGenerator;

    public MainTokenManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = new ExpirableTokenGenerator();
        this.userIdGenerator = new UserIdGenerator();
    }

    @Override
    public CerberAuthDecission generateTokenAndSave(@TokenData TokenEstablishData establishData) {
        if (checkIsExist(establishData)) {
            return buildCerberAuthDecission(false, null,
                    "User just exist in database so it is impossible to create new same account.");
        }

        AbstractSev2Token token = tokenGenerator.generateToken(userIdGenerator.generateId(establishData.getUsername()),
                establishData.getUsername(), establishData.getIpAddress());

        AbstractSev2Token savedToken = tokenRepository.save(token);
        String encodedToken = tokenGenerator.encodeToken(savedToken);

        return buildCerberAuthDecission(true, encodedToken, "Authentication with success.");
    }

    private boolean checkIsExist(TokenEstablishData tokenEstablishData) {
        return tokenRepository.findByUsername(tokenEstablishData.getUsername()).isPresent();
    }

    /**
     * This method is used to create new token for user that exist in database
     * */
    @Override
    public CerberAuthDecission updateToken(TokenEstablishData establishData) {
        String userId = fetchUserId(establishData.getUsername());

        if (userId == null) {
            log.info("userId is null");
            return buildCerberAuthDecission(false, null, "User not exist in database.");
        }

        AbstractSev2Token token = tokenGenerator.generateToken(userId, establishData.getUsername(), establishData.getIpAddress());
        AbstractSev2Token updatedToken = tokenRepository.update(token);

        String encodedToken = tokenGenerator.encodeToken(updatedToken);
        return buildCerberAuthDecission(true, encodedToken, "Authentication with success.");
    }

    private String fetchUserId(String username) {
        AbstractSev2Token tokenEntity = tokenRepository.findByUsername(username).orElse(null);

        if (tokenEntity == null || tokenEntity.getUserId() == null) {
            return null;
        }

        return String.valueOf(tokenEntity.getUserId());
    }

    private CerberAuthDecission buildCerberAuthDecission(boolean decission, String encodedToken, String message) {
        CerberAuthDecission cerberAuthDecission = new CerberAuthDecission();
        cerberAuthDecission.setDecision(decission);
        cerberAuthDecission.setSev2token(encodedToken);
        cerberAuthDecission.setMessage(message);
        return cerberAuthDecission;
    }

}