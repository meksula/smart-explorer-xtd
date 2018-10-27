package pl.smartexplorer.cerber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;
import pl.smartexplorer.sev2Token.model.Sev2TokenType;
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
@Repository
public class ExpirableTokenRepository implements TokenRepository {
    private final static String CREATE_TABLE = "CREATE TABLE sev2token(user_id VARCHAR NOT NULL, " +
            "username VARCHAR(255), sev2_token_type VARCHAR(255), sev2uuid VARCHAR(255), date VARCHAR(255), " +
            "ip_address VARCHAR(255), isexpired VARCHAR (10), primary key(user_id));";
    private final static String EXCEPTION_MSG = "Cannot save entity to database. Some error occurred.";
    private JdbcTemplate jdbcTemplate;

    public ExpirableTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            jdbcTemplate.execute(CREATE_TABLE);
        } catch (Exception exception) {
            log.info("Do not create table because it is exist currently in database");
        }
    }

    @Override
    public AbstractSev2Token save(AbstractSev2Token token) {
        final String SAVE_QUERY = "INSERT INTO sev2token (user_id, username, sev2_token_type, " +
                "sev2uuid, date, ip_address, isexpired) VALUES (?, ?, ?, ?, ?, ?, ?);";
        Sev2TokenExpirable expirable = (Sev2TokenExpirable) token;
        int rows = jdbcTemplate.update(SAVE_QUERY, expirable.getUserId(), expirable.getUsername(), expirable.getTokenType().name(),
                expirable.getSev2Uiid().toString(), expirable.getDate(), expirable.getIpAddress(), expirable.isExpired());

        if (rows == 1)
            return expirable;
        else
            throw new SmartExplorerRepositoryException(EXCEPTION_MSG);
    }

    /**
     * In order to use this method entity should exist in database with some user_id.
     * New object in method params will replace old entity
     * */
    @Override
    public AbstractSev2Token update(AbstractSev2Token token) {
        final String UPDATE_QUERY = "UPDATE sev2token SET username = ?, sev2_token_type = ?, sev2uuid = ?, ip_address = ?, isexpired = ? WHERE user_id = ?;";
        Sev2TokenExpirable tokenExpirable = (Sev2TokenExpirable) token;
        int rows = jdbcTemplate.update(UPDATE_QUERY, tokenExpirable.getUsername(), tokenExpirable.getTokenType().toString(), tokenExpirable.getSev2Uiid().toString(),
                tokenExpirable.getIpAddress(), tokenExpirable.isExpired(), tokenExpirable.getUserId());

        if (rows == 1)
            return tokenExpirable;
        else
            throw new SmartExplorerRepositoryException(EXCEPTION_MSG);
    }

    @Override
    public boolean delete(AbstractSev2Token token) {
        final String DELETE_QUERY = "DELETE FROM sev2token WHERE user_id = ?;";
        int rows = jdbcTemplate.update(DELETE_QUERY, token.getUserId());
        return rows == 1;
    }

    @Override
    public boolean deleteByUserId(String userId) {
        final String DELETE_QUERY = "DELETE FROM sev2token WHERE user_id = ?;";
        int rows = jdbcTemplate.update(DELETE_QUERY, userId);
        return rows == 1;
    }

    @Override
    public Optional<AbstractSev2Token> findByUserId(String userId) {
        final String FIND_BY_USER_ID_QUERY = "SELECT * FROM sev2token WHERE user_id = ?;";
        Sev2TokenExpirable token;
        try {
            token = jdbcTemplate.queryForObject(FIND_BY_USER_ID_QUERY, rowMapper, userId);
        } catch (EmptyResultDataAccessException exception) {
            token = null;
        }
        return Optional.ofNullable(token);
    }

    @Override
    public Optional<AbstractSev2Token> findByUsername(String username) {
        final String FIND_BY_USERNAME_QUERY = "SELECT * FROM sev2token WHERE username = ?;";
        Sev2TokenExpirable token;
        try {
            token = jdbcTemplate.queryForObject(FIND_BY_USERNAME_QUERY, rowMapper, username);
        } catch (EmptyResultDataAccessException exception) {
            token = null;
        }

        return Optional.ofNullable(token);
    }

    @Override
    public void dropTable() {
        jdbcTemplate.execute("drop table sev2token;");
    }

    private final RowMapper<Sev2TokenExpirable> rowMapper = (resultSet, rowNum) -> {
        Sev2TokenExpirable token = new Sev2TokenExpirable(resultSet.getString("user_id"),
                resultSet.getString("username"));

        token.setTokenType(Sev2TokenType.valueOf(resultSet.getString("sev2_token_type")));
        token.setSev2Uiid(UUID.fromString(resultSet.getString("sev2uuid")));
        token.setDate(parseString(resultSet.getString("date")));
        token.setIpAddress(resultSet.getString("ip_address"));
        token.setExpired(resultSet.getBoolean("isexpired"));
        return token;
    };

    private LocalDateTime parseString(String date) {
        String[] parts = date.split("\\s");
        String filled = parts[0].concat("T").concat(parts[1]).trim();
        return LocalDateTime.parse(filled);
    }

}
