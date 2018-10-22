package pl.smartexplorer.cerber.repository;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

public interface TokenRepository {
    AbstractSev2Token save(AbstractSev2Token token);

    AbstractSev2Token update(AbstractSev2Token token);

    AbstractSev2Token delete(AbstractSev2Token token);

    AbstractSev2Token findByUserId(String userId);

    AbstractSev2Token findByUsername(String username);
}
