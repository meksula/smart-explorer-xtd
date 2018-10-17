package pl.smartexplorer.sev2Token.core.data.exec;

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(Sev2TokenExpirableJdbcExecutor.class);

    public Sev2TokenExpirableJdbcExecutor(DatabasesAvailable database, final String databaseAddress, final String username,
                                          final String password) {
        this.database = database;
        this.jdbcDriver = database.getDatabaseDriver();
        this.databaseAddress = databaseAddress;
        this.username = username;
        this.password = password;
        logger.info(this.getClass() + " just instantiated.");
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

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Cannot save entity. Exception was thrown.");
        } finally {
            finalizeStatement();
        }

        return token;
    }

    private void finalizeStatement() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Cannot properly close SQL statement or connection.");
        }
    }

    @Override
    public AbstractSev2Token updateToken(AbstractSev2Token token) {
        Sev2TokenExpirable tokenExpirable = (Sev2TokenExpirable) token;
        final String SAVE_QUERY = "UPDATE " + TABLE_NAME_DEFAULT +
                " SET sev2TokenType = ?, uuid = ?, issue_date = ?, ip_address = ?, isExpired = ? WHERE userId = ?;";
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

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getClass() + " was thrown. Update not processed.");
        } finally {
            finalizeStatement();
        }

        return token;
    }

    @Override
    public void deleteEntity(String userId) {
        PreparedStatement statement;

        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            statement = connection.prepareStatement("DELETE FROM " + TABLE_NAME_DEFAULT + " WHERE userId = ?;");
            statement.setString(1, userId);

            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getClass() + " was thrown. Delete not processed.");
        } finally {
            finalizeStatement();
        }
    }

    @Override
    public Optional<AbstractSev2Token> fetchByUserId(String userId) {
        PreparedStatement statement;
        Sev2TokenExpirable tokenExpirable = null;

        try {
            Class.forName(jdbcDriver);
            this.connection = DriverManager.getConnection(databaseAddress, username, password);
            statement = this.connection.prepareStatement("SELECT * FROM " + TABLE_NAME_DEFAULT + " WHERE userId = ?");
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();
            
            tokenExpirable = buildToken(resultSet);
            
            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getClass() + " was thrown. Delete not processed.");
        } finally {
            finalizeStatement();
        }

        return Optional.ofNullable(tokenExpirable);
    }

    private Sev2TokenExpirable buildToken(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String username = resultSet.getString("username");
                String sev2TokenType = resultSet.getString("sev2TokenType");
                String uuid = resultSet.getString("uuid");
                String issue_date = resultSet.getString("issue_date");
                String ip_address = resultSet.getString("ip_address");
                String isExpired = resultSet.getString("isExpired");

                Sev2TokenExpirable tokenExpirable = new Sev2TokenExpirable(userId, username);
                tokenExpirable.setTokenType(Sev2TokenType.valueOf(sev2TokenType));
                tokenExpirable.setSev2Uiid(UUID.fromString(uuid));
                tokenExpirable.setDate(LocalDateTime.parse(issue_date));
                tokenExpirable.setIpAddress(ip_address);
                tokenExpirable.setExpired(Boolean.parseBoolean(isExpired));
                return tokenExpirable;
            }

        } catch (SQLException e) {
            logger.error("Entity is not able to fetch or mapping to Java object.");
        }

        return null;
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
            finalizeStatement();
        }
    }

}
