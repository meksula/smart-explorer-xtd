package pl.smartexplorer.cerber.security;

import pl.smartexplorer.cerber.dto.TokenEstablishData;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public interface TokenManager {
    String generateTokenAndSave(TokenEstablishData establishData);
}
