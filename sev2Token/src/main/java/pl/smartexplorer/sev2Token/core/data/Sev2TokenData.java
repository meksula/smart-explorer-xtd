package pl.smartexplorer.sev2Token.core.data;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public interface Sev2TokenData {
    AbstractSev2Token save(AbstractSev2Token token);

    AbstractSev2Token update(AbstractSev2Token token);

    Optional<AbstractSev2Token> fetchByUserId(String userId);

    Optional<AbstractSev2Token> fetchByUsername(String username);

    boolean createTable();
}
