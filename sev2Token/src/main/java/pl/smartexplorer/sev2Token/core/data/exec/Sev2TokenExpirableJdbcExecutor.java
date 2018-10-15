package pl.smartexplorer.sev2Token.core.data.exec;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import java.sql.*;
import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

public class Sev2TokenExpirableJdbcExecutor implements JdbcExecutor {
    private DatabasesAvailable database;
    private String jdbcDriver;
    private String databaseAddress;
    private String username;
    private String password;
    private Connection connection = null;
    private Statement statement = null;
    private final static String TABLE_NAME_DEFAULT = "sev2token";

    public Sev2TokenExpirableJdbcExecutor(DatabasesAvailable database, final String databaseAddress, final String username,
                                          final String password) {
        this.database = database;
        this.jdbcDriver = database.getDatabaseDriver();
        this.databaseAddress = databaseAddress;
        this.username = username;
        this.password = password;
    }

    @Override
    public AbstractSev2Token saveToken(AbstractSev2Token token) {
        Sev2TokenExpirable tokenExpirable = (Sev2TokenExpirable) token;
        final String SAVE_QUERY = "INSERT INTO " + TABLE_NAME_DEFAULT +
                " (userId, username, sev2TokenType, uuid, issue_date, ip_address, isExpired) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = null;

        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            statement = connection.prepareStatement(SAVE_QUERY);

            statement.setString(1, tokenExpirable.getUserId());
            statement.setString(2, tokenExpirable.getUsername());
            statement.setString(3, tokenExpirable.getTokenType().name());
            statement.setString(4, tokenExpirable.getSev2Uiid().toString());
            statement.setString(5, tokenExpirable.getDate().toString());
            statement.setString(6, tokenExpirable.getIpAddress());
            statement.setString(7, String.valueOf(tokenExpirable.isExpired()));

            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return token;
    }

    @Override
    public AbstractSev2Token updateToken(AbstractSev2Token token) {
        Sev2TokenExpirable tokenExpirable = (Sev2TokenExpirable) token;
        final String SAVE_QUERY = "UPDATE " + TABLE_NAME_DEFAULT +
                " SET sev2TokenType = ?, uuid = ?, issue_date = ?, ip_address = ?, isExpired = ? WHERE userid = ?;";
        PreparedStatement statement = null;

        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            statement = connection.prepareStatement(SAVE_QUERY);

            statement.setString(1, tokenExpirable.getTokenType().name());
            statement.setString(2, tokenExpirable.getSev2Uiid().toString());
            statement.setString(3, tokenExpirable.getDate().toString());
            statement.setString(4, tokenExpirable.getIpAddress());
            statement.setString(5, String.valueOf(tokenExpirable.isExpired()));
            statement.setString(6, tokenExpirable.getUserId());

            deleteEntity(token.getUserId());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return token;
    }

    @Override
    public void deleteEntity(String userId) {
        PreparedStatement statement = null;

        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            statement = connection.prepareStatement("DELETE FROM " + TABLE_NAME_DEFAULT + " WHERE userId = ?;");

            statement.setString(1, userId);

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
    public void createTable() {
        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            this.statement = connection.createStatement();
            this.statement.executeUpdate(database.getTableCreationQuery(TABLE_NAME_DEFAULT));
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
