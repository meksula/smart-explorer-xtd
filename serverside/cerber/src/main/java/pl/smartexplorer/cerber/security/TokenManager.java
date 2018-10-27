package pl.smartexplorer.cerber.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.TokenEstablishData;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public interface TokenManager {
    CerberAuthDecission generateTokenAndSave(TokenEstablishData establishData);

    CerberAuthDecission updateToken(TokenEstablishData establishData) throws JsonProcessingException;
}
