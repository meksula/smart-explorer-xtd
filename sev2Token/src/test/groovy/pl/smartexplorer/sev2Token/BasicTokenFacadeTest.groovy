package pl.smartexplorer.sev2Token

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable
import pl.smartexplorer.sev2Token.core.data.exec.Sev2TokenExpirableJdbcExecutor
import pl.smartexplorer.sev2Token.model.Sev2TokenType
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 16-10-2018
 * */

class BasicTokenFacadeTest extends Specification {
    final String DB_ADDRESS_MYSQL = "jdbc:mysql://localhost:3306/chat?useUnicode=yes&characterEncoding=UTF-8"
    private TokenFacade tokenFacade
    def userId = "211411"

    def setup() {
        this.tokenFacade = new BasicTokenFacade(Sev2TokenType.EXPIRABLE, 120, DatabasesAvailable.MYSQL, DB_ADDRESS_MYSQL, "chat", "chatdefault")
    }

    def "generate and save token AND allow access test"() {
        when:
        def token = tokenFacade.generateAndSaveToken(userId, "karoladmin", "123.33.444.54")
        println(token)

        then:
        assert tokenFacade.allowAccess(userId, token)
    }

    def cleanup() {
        new Sev2TokenExpirableJdbcExecutor(
                DatabasesAvailable.MYSQL, DB_ADDRESS_MYSQL, "chat", "chatdefault").deleteEntity(userId)
    }

}
