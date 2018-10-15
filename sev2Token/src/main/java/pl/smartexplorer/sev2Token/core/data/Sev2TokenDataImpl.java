package pl.smartexplorer.sev2Token.core.data;

import pl.smartexplorer.sev2Token.core.data.exec.JdbcExecutor;
import pl.smartexplorer.sev2Token.core.data.exec.JdbcExecutorFactory;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public class Sev2TokenDataImpl implements Sev2TokenData {
    private DatabasesAvailable database;
    private JdbcExecutor jdbcExecutor;

    public Sev2TokenDataImpl(final DatabasesAvailable databasesAvailable, final Sev2TokenType tokenType,
                             final String databaseAddress, final String username, final String password) {
        this.database = databasesAvailable;
        this.jdbcExecutor = new JdbcExecutorFactory(database)
                .getJdbcExecutor(tokenType.getTokenClass(), databaseAddress, username, password);
    }

    @Override
    public AbstractSev2Token save(AbstractSev2Token token) {
        return jdbcExecutor.saveToken(token);
    }

    @Override
    public AbstractSev2Token update(AbstractSev2Token token) {
        return jdbcExecutor.updateToken(token);
    }

    @Override
    public Optional<AbstractSev2Token> fetchByUserId(String userId) {
        return jdbcExecutor.fetchByUserId(userId);
    }

    @Override
    public Optional<AbstractSev2Token> fetchByUsername(String username) {
        return jdbcExecutor.fetchByUsername(username);
    }

    @Override
    public boolean createTable() {
        try {
            jdbcExecutor.createTable();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
