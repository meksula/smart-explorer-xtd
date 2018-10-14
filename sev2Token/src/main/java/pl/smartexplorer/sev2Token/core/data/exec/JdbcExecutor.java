package pl.smartexplorer.sev2Token.core.data.exec;

import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public interface JdbcExecutor {
    AbstractSev2Token saveToken(AbstractSev2Token token);

    AbstractSev2Token updateToken(AbstractSev2Token token);

    Optional<AbstractSev2Token> fetchByUserId(String userId);

    Optional<AbstractSev2Token> fetchByUsername(String username);

    void createTable(String tableCreationQuery);
}
