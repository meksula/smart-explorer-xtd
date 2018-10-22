package pl.smartexplorer.cerber.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.smartexplorer.sev2Token.model.AbstractSev2Token;

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
@Repository
public class ExpirableTokenRepository implements TokenRepository {
    private final static String CREATE_TABLE = "CREATE TABLE sev2token(user_id VARCHAR(255) NOT NULL, " +
            "username VARCHAR(255), sev2_token_type VARCHAR(255), sev2uuid VARCHAR(255), date VARCHAR(255), " +
            "ip_address VARCHAR(255), isExpired VARCHAR (10), primary key(user_id));";
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
        return null;
    }

    @Override
    public AbstractSev2Token update(AbstractSev2Token token) {
        return null;
    }

    @Override
    public AbstractSev2Token delete(AbstractSev2Token token) {
        return null;
    }

    @Override
    public AbstractSev2Token findByUserId(String userId) {
        return null;
    }

    @Override
    public AbstractSev2Token findByUsername(String username) {
        return null;
    }
}
