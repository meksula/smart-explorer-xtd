package pl.smartexplorer.sev2Token.core.data.exec;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.exception.Sev2TokenException;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public class JdbcExecutorFactory {
    private DatabasesAvailable database;

    public JdbcExecutorFactory(DatabasesAvailable database) {
        this.database = database;
    }

    public JdbcExecutor getJdbcExecutor(Class<?> type, final String databaseAddress, final String username, final String password) {
        if (type.equals(Sev2TokenExpirable.class)) {
            return new Sev2TokenExpirableJdbcExecutor(database, databaseAddress, username, password);
        }

        else throw new Sev2TokenException("Invalid entity classname. Not accepted type.");
    }

}
