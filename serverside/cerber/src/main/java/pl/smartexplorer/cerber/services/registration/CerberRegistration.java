package pl.smartexplorer.cerber.services.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.repository.UserRepository;
import pl.smartexplorer.cerber.security.TokenManager;
import pl.smartexplorer.sev2Token.core.matcher.RetrieveTokenData;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Service
public class CerberRegistration extends UserRegistrator {
    private TokenManager tokenManager;

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public CerberRegistration(UserRepository userRepository, TokenRepository tokenRepository) {
        super(userRepository, tokenRepository);
    }

    @Override
    protected String generateToken(TokenEstablishData establishData) {
        String encodedToken = tokenManager.generateTokenAndSave(establishData).getSev2token();
        return new RetrieveTokenData().extractParameter(encodedToken, RetrieveTokenData.TokenParams.USER_ID);
    }

}
