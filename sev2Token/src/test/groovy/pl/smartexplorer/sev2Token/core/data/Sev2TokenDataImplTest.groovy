package pl.smartexplorer.sev2Token.core.data

import pl.smartexplorer.sev2Token.model.Sev2TokenType
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 14-10-2018
 * */

class Sev2TokenDataImplTest extends Specification {
    final String DB_ADDRESS_MYSQL = "jdbc:mysql://localhost:3306/chat?useUnicode=yes&characterEncoding=UTF-8"
    final String DB_ADDRESS_POSGRES = "jdbc:postgresql://localhost:5432/napiwo"
    final String USERNAME = "chat"
    final String PASSWORD = "chatdefault"

    final String USERNAME_POSTGRES = "karol"
    final String PASSWORD_POSTGRES = "karolpass"
    private Sev2TokenData sev2TokenData

    def setup() {
        this.sev2TokenData = new Sev2TokenDataImpl(DatabasesAvailable.MYSQL, Sev2TokenType.EXPIRABLE, DB_ADDRESS_MYSQL, USERNAME, PASSWORD)
    }

    def "creation table in mysql database test"() {
        given:
        final String SQL_QUERY = "CREATE TABLE sev2token (id INTEGER NOT NULL AUTO_INCREMENT, userId VARCHAR(255)," +
                "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), PRIMARY KEY(id));"

        expect:
        sev2TokenData.createTable()
    }

    def "creation table in Postgresql database test"() {
        given:
        final String SQL_QUERY_POSTGRES = "CREATE TABLE sev2token (id INTEGER NOT NULL, userId VARCHAR(255)," +
                "username VARCHAR(255), sev2TokenType VARCHAR (255), uuid VARCHAR (255), PRIMARY KEY(id));"

        def tokenData = new Sev2TokenDataImpl(DatabasesAvailable.POSTGRESQL, Sev2TokenType.EXPIRABLE, DB_ADDRESS_POSGRES, USERNAME_POSTGRES, PASSWORD_POSTGRES)

        expect:
        tokenData.createTable()
    }

}
