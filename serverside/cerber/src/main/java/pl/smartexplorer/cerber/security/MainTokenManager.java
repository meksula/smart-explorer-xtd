package pl.smartexplorer.cerber.security;

import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.validators.TokenData;
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator;
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Service
public class MainTokenManager implements TokenManager {
    private TokenRepository tokenRepository;
    private TokenGenerator tokenGenerator;

    public MainTokenManager(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.tokenGenerator = new ExpirableTokenGenerator();
    }

    @Override
    public String generateTokenAndSave(@TokenData TokenEstablishData establishData) {
        if (checkIsExist(establishData.getUserId())) {
            throw new SmartExplorerRepositoryException("You cannot create new entity because now, you have your login entity.\n" +
                    "Please use this endpoint to update your token: /api/v2/update");
        }

        AbstractSev2Token token = tokenGenerator.generateToken(establishData.getUserId(), establishData.getUsername(),
                establishData.getIpAddress());

        AbstractSev2Token savedToken = tokenRepository.save(token);
        return tokenGenerator.encodeToken(savedToken);
    }

    private boolean checkIsExist(String userId) {
        return tokenRepository.findByUserId(userId).isPresent();
    }

    /**
     * This method is used to create new token for user that exist in database
     * */
    @Override
    public String updateTokenAndUpdate(TokenEstablishData establishData) {
        AbstractSev2Token token = tokenGenerator.generateToken(establishData.getUserId(), establishData.getUsername(),
                establishData.getIpAddress());

        AbstractSev2Token updatedToken = tokenRepository.update(token);
        return tokenGenerator.encodeToken(updatedToken);
    }

}
