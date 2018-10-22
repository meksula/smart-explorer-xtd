package pl.smartexplorer.sev2Token.fragmented;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public interface FragmentedTokenFacade {
    AbstractSev2Token generateToken(String userId, String username, String ipAddress);

    String encodeToken(AbstractSev2Token token);

    String generateAndEncode(String userId, String username, String ipAddress);

    boolean isExpired(AbstractSev2Token token);

    boolean isExpired(String encoded);

    boolean allow(String encodedFromRequest, AbstractSev2Token tokenFromDatabase);
}
