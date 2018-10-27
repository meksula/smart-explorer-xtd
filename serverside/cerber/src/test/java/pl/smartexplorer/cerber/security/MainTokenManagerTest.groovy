package pl.smartexplorer.cerber.security

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import pl.smartexplorer.cerber.dto.TokenEstablishData
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.cerber.repository.TokenRepository
import spock.lang.Specification


/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@Slf4j
@SpringBootTest
class MainTokenManagerTest extends Specification {

    @Autowired
    MainTokenManager tokenManager

    @Autowired
    JdbcTemplate jdbcTemplate

    TokenRepository tokenRepository

    def tokenEstablishData = null

    def setup() {
        tokenRepository = new ExpirableTokenRepository(jdbcTemplate)

        tokenEstablishData = new TokenEstablishData()
        tokenEstablishData.setUserId(null)
        tokenEstablishData.setUsername("mikolajkopernik")
        tokenEstablishData.setIpAddress("192.33.22.11")
    }

    def "generateTokenAndSave SUCCESS test"() {
        when:
        def encodedToken = tokenManager.generateTokenAndSave(tokenEstablishData)

        then:
        encodedToken != null
    }

    def "generateTokenAndSave FAILED test"() {
        setup:
        tokenManager.generateTokenAndSave(tokenEstablishData)

        when:
        def encodedTokenDuplicated = tokenManager.generateTokenAndSave(tokenEstablishData)

        then:
        !encodedTokenDuplicated.isDecision()
        encodedTokenDuplicated.getMessage() == "User just exist in database so it is impossible to create new same account."
        encodedTokenDuplicated.sev2token == null
    }

    def "updateToken SUCCESS test"() {
        setup:
        tokenManager.generateTokenAndSave(tokenEstablishData)

        when:
        def updatedSuccessfully = tokenManager.updateToken(tokenEstablishData)

        then:
        assert updatedSuccessfully.decision
        updatedSuccessfully.sev2token != null
        updatedSuccessfully.message == "Authentication with success."
    }

    def "updateToken FAILED test"() {
        when:
        def updatedFailed = tokenManager.updateToken(tokenEstablishData)

        then:
        !updatedFailed.decision
        updatedFailed.sev2token == null
        updatedFailed.message == "User not exist in database."
    }

    def cleanup() {
        tokenRepository.dropTable()
    }

}
