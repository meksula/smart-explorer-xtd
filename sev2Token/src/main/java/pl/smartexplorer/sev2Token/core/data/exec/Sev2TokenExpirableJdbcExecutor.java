package pl.smartexplorer.sev2Token.core.data.exec;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public class Sev2TokenExpirableJdbcExecutor implements JdbcExecutor {
    private String jdbcDriver;
    private String databaseAddress;
    private String username;
    private String password;
    private Connection connection = null;
    private Statement statement = null;
    
    public Sev2TokenExpirableJdbcExecutor(DatabasesAvailable database, final String databaseAddress, final String username,
                                          final String password) {
        this.jdbcDriver = database.getDatabaseDriver();
        this.databaseAddress = databaseAddress;
        this.username = username;
        this.password = password;
    }

    @Override
    public AbstractSev2Token saveToken(AbstractSev2Token token) {
        return null;
    }

    @Override
    public AbstractSev2Token updateToken(AbstractSev2Token token) {
        return null;
    }

    @Override
    public Optional<AbstractSev2Token> fetchByUserId(String userId) {
        return Optional.empty();
    }

    @Override
    public Optional<AbstractSev2Token> fetchByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void createTable(final String SQL_QUERY) {
        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            this.statement = connection.createStatement();
            this.statement.executeUpdate(SQL_QUERY);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
