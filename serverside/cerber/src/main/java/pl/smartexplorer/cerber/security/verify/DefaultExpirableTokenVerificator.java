package pl.smartexplorer.cerber.security.verify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.repository.TokenRepository;

/**
 * @author
 * Karol Meksuła
 * 28-10-2018
 * */

@Slf4j
@Service
public class DefaultExpirableTokenVerificator extends ExpirableTokenVerificator {

    //each service implements ExpirableTokenVerificator
    public DefaultExpirableTokenVerificator(TokenRepository tokenRepository) {
        super(tokenRepository);
    }

}
