package pl.smartexplorer.cerber.security.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.repository.UserRepository;
import pl.smartexplorer.cerber.security.TokenManager;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@Slf4j
@Service
public class UserRequestGatewayImpl implements UserRequestGateway {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenManager tokenManager;

    public UserRequestGatewayImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
        log.info("Password encoder class: " + passwordEncoder.getClass());
    }

    @Override
    public User allowReturnUserEntity(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Cannot fetch user entity with username: " + username));

        boolean usernameEquals = user.getUsername().equals(username);
        boolean passwordsEquals = passwordEncoder.matches(password, user.getEncryptedPassword());

        if (usernameEquals && passwordsEquals) {
            log.info("User correctly authenticated.");
            return user;
        }

        log.info("Authentication attempt refused.");
        return null;
    }

    /**
     * Method using for retrieve user entity and cerber auth decision in one request.
     * */
    @Override
    public CerberAuthDecission allowReturnCerberDesision(String username, String password, HttpServletRequest request) throws JsonProcessingException {
        User user = this.allowReturnUserEntity(username, password);

        if (user == null) {
            log.info("Unfortunately cannot retrieve user from database.");
            throw new SmartExplorerRepositoryException("Something went wrong. By unrecognized reasons user entity is not found.");
        }

        CerberAuthDecission cerberAuthDecission =
                tokenManager.updateToken(buildTokenEstablishData(user.getUserId(), username, request.getRemoteAddr()));
        cerberAuthDecission.setUser(user);

        if (!cerberAuthDecission.isDecision() && user != null) {
            cerberAuthDecission.setMessage("User retrieved successfully but some error occured while sev2token update");
        }

        return cerberAuthDecission;
    }

    private TokenEstablishData buildTokenEstablishData(String userId, String username, String remoteAddr) {
        TokenEstablishData tokenEstablishData = new TokenEstablishData();
        tokenEstablishData.setUserId(userId);
        tokenEstablishData.setUsername(username);
        tokenEstablishData.setIpAddress(remoteAddr);

        return tokenEstablishData;
    }

}
