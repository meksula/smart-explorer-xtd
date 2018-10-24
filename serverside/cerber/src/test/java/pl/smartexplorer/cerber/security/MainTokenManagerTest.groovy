package pl.smartexplorer.cerber.security

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.cerber.dto.TokenEstablishData
import pl.smartexplorer.cerber.exception.SmartExplorerRepositoryException
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator
import pl.smartexplorer.sev2Token.core.matcher.ExpirableTokenMatcher
import pl.smartexplorer.sev2Token.model.AbstractSev2Token
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
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
    ExpirableTokenRepository repository

    TokenEstablishData tokenEstablishData
    TokenEstablishData invalidEstablishData

    def validUserId = "2942432"
    def validUsername = "karoladmin"
    def validIpAddress = "192.392.292.22"

    def setup() {
        this.tokenEstablishData = new TokenEstablishData(validUserId, validUsername, validIpAddress)
        this.invalidEstablishData = new TokenEstablishData(validUserId, validUsername, "129922993")
    }

    def "Manager should correctly generate token"() {
        when:
        def encodedToken = tokenManager.generateTokenAndSave(tokenEstablishData)
        println(encodedToken)

        then:
        encodedToken != null
    }

    def "Manager should correctly generate token and save to database"() {
        when:
        def encodedToken = tokenManager.generateTokenAndSave(tokenEstablishData)
        println(encodedToken)

        then:
        encodedToken != null
        AbstractSev2Token token = repository.findByUserId(validUserId).get()
        assert new ExpirableTokenMatcher(100).allowAccess(encodedToken, token)
    }

    def "update token of currently exist user"() {
        when: "token is just saved"
        def encodedToken = tokenManager.generateTokenAndSave(tokenEstablishData)

        then:
        tokenManager.updateTokenAndUpdate(tokenEstablishData) // update token of a same user
        AbstractSev2Token updatedToken = repository.findByUserId(validUserId).get()//fetch updated, changed token from database
        updatedToken != null
        !new ExpirableTokenMatcher(100).allowAccess(encodedToken, updatedToken)
        assert new ExpirableTokenMatcher(100).allowAccess(new ExpirableTokenGenerator().encodeToken(updatedToken), updatedToken)
    }

    def "try to create new entity to exist user : should throw exception"() {
        setup: "save one entity"
        tokenManager.generateTokenAndSave(tokenEstablishData)

        when: "try to save entity with same user_id"
        tokenManager.generateTokenAndSave(tokenEstablishData)

        then:
        thrown(SmartExplorerRepositoryException.class)
    }

    //should cleanup because some method save sth to db
    void cleanup() {
        Sev2TokenExpirable expirable = new Sev2TokenExpirable(validUserId, validUsername)
        repository.delete(expirable)
    }

}
