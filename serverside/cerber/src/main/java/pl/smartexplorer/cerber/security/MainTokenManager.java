package pl.smartexplorer.cerber.security;

import org.springframework.stereotype.Service;
import pl.smartexplorer.cerber.dto.TokenEstablishData;
import pl.smartexplorer.cerber.validators.TokenData;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Service
public class MainTokenManager implements TokenManager {

    @Override
    public String generateTokenAndSave(@TokenData TokenEstablishData establishData) {



        return establishData.toString();
    }

}
