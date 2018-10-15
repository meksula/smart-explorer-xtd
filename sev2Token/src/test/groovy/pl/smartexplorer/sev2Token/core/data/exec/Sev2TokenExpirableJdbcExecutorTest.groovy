package pl.smartexplorer.sev2Token.core.data.exec

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable
import pl.smartexplorer.sev2Token.model.Sev2TokenType
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author
 * Karol Meksuła
 * 15-10-2018
 * */

class Sev2TokenExpirableJdbcExecutorTest extends Specification {
    final String DB_ADDRESS_MYSQL = "jdbc:mysql://localhost:3306/chat?useUnicode=yes&characterEncoding=UTF-8"
    private Sev2TokenExpirableJdbcExecutor executor
    private Sev2TokenExpirable sev2TokenExpirable
    final String USERNAME = "chat"
    final String PASSWORD = "chatdefault"

    def userId = "2114411"
    def username = "karoladmin"
    def ipAddress = "134.43.33.45"

    void setup() {
        this.executor = new Sev2TokenExpirableJdbcExecutor(DatabasesAvailable.MYSQL, DB_ADDRESS_MYSQL, USERNAME, PASSWORD)

        this.sev2TokenExpirable = new Sev2TokenExpirable(userId, username)
        this.sev2TokenExpirable.setTokenType(Sev2TokenType.EXPIRABLE)
        this.sev2TokenExpirable.setSev2Uiid(UUID.randomUUID())
        this.sev2TokenExpirable.setDate(LocalDateTime.now())
        this.sev2TokenExpirable.setIpAddress(ipAddress)
        this.sev2TokenExpirable.setExpired(false)
    }

    def "save entity to database"() {
        setup:
        executor.createTable()

        expect:
        executor.saveToken(sev2TokenExpirable)
    }

    def "update saved before entity in database"() {
        setup:
        executor.createTable()

        expect:
        sev2TokenExpirable.setExpired(true)
        sev2TokenExpirable.setIpAddress("11.111.111.11")
        executor.saveToken(sev2TokenExpirable)
    }

    def "delete entity test"() {
        expect:
        executor.deleteEntity(userId)
    }

}
