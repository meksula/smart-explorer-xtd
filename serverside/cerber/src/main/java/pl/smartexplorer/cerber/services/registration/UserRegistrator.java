package pl.smartexplorer.cerber.services.registration;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.cerber.dto.CerberAuthDecisionRegistration;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.repository.TokenRepository;
import pl.smartexplorer.cerber.repository.UserRepository;
import pl.smartexplorer.cerber.validators.classes.UserRegistrationValidator;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Slf4j
public abstract class UserRegistrator implements RegistrationVerificator {
    private UserRegistrationValidator validator;
    private UserRepository userRepository;

    public UserRegistrator(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.validator = new UserRegistrationValidator(userRepository, tokenRepository);
    }

    protected abstract String generateToken(TokenEstablishData buildTokenEstablishData);
    protected abstract String getSev2Token();
    protected abstract String initVerificationUiid();

    public CerberAuthDecisionRegistration registerUser(User user, String ipAddress) {
        boolean flag = validator.isAbleToRegister(user);

        if (!flag) {
            final String MSG = "Validator not allow to create new user. User with same, identical username :" +
                                user.getUsername() + " just exist in database.";
            log.error(MSG);

            return buildDecisionRegistration(user, false, MSG, null, null);
        }

        String userId = generateToken(buildTokenEstablishData(user.getUsername(), ipAddress));
        user.setUserId(userId);
        User updatedUser = saveUser(user);
        return buildDecisionRegistration(updatedUser, true,
                "User was created successfully", getSev2Token(), initVerificationUiid());
    }

    private TokenEstablishData buildTokenEstablishData(String username, String ipAddress) {
        TokenEstablishData tokenEstablishData = new TokenEstablishData();
        tokenEstablishData.setUserId(null);
        tokenEstablishData.setUsername(username);
        tokenEstablishData.setIpAddress(ipAddress);
        return tokenEstablishData;
    }

    private User saveUser(User user) {
        if (user.getUserId() == null) {
            log.error("User was not saved to database.");
        }

        return this.userRepository.save(user);
    }

    private CerberAuthDecisionRegistration buildDecisionRegistration(User user, boolean decission, String message,
                                                                     String sev2token, String verificationUiid) {
        CerberAuthDecisionRegistration cadr = new CerberAuthDecisionRegistration();
        cadr.setUser(user);
        cadr.setDecision(decission);
        cadr.setMessage(message);
        cadr.setSev2token(sev2token);
        cadr.setVerificationUuid(verificationUiid);
        return cadr;
    }

}
