package pl.smartexplorer.cerber.services.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.repository.RegistrationVerifRepository;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.repository.UserRepository;
import pl.smartexplorer.cerber.security.TokenManager;
import pl.smartexplorer.cerber.services.registration.dto.RegistrationVerif;
import pl.smartexplorer.sev2Token.core.matcher.RetrieveTokenData;

import java.util.Optional;
import java.util.UUID;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Slf4j
@Service
public class CerberRegistration extends UserRegistrator {
    private TokenManager tokenManager;
    private RegistrationVerifRepository verifRepository;
    private UserRepository userRepository;
    private String encodedToken;
    private String currentUserId;

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Autowired
    public void setVerifRepository(RegistrationVerifRepository verifRepository) {
        this.verifRepository = verifRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CerberRegistration(UserRepository userRepository, TokenRepository tokenRepository) {
        super(userRepository, tokenRepository);
    }

    @Override
    protected String generateToken(TokenEstablishData establishData) {
        this.encodedToken = tokenManager.generateTokenAndSave(establishData).getSev2token();
        this.currentUserId = new RetrieveTokenData().extractParameter(encodedToken, RetrieveTokenData.TokenParams.USER_ID);
        return currentUserId;
    }

    @Override
    protected String getSev2Token() {
        return this.encodedToken;
    }

    @Override
    protected String initVerificationUiid() {
        RegistrationVerif registrationVerif = new RegistrationVerif();
        registrationVerif.setUserId(this.currentUserId);
        registrationVerif.setUuid(UUID.randomUUID().toString());

        return verifRepository.save(registrationVerif).getUuid();
    }

    @Override
    public boolean verifyRegistration(String userId, String verificationUuid) {
        Optional<RegistrationVerif> verif = verifRepository.findById(userId);

        if (!verif.isPresent()) {
            log.info("Path variables are invalid.");
            return false;
        } else {
            RegistrationVerif registrationVerif = verif.get();
            boolean result = userId.equals(registrationVerif.getUserId())
                    && verificationUuid.equals(registrationVerif.getUuid());

            verifRepository.delete(registrationVerif);
            enableUser(userId);
            return result;
        }
    }

    private void enableUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SmartExplorerRepositoryException("Unexpected error occured. " +
                        "Cannot find user in database. It may means that database is disabled."));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        log.info("User verified with success.");
    }

}
