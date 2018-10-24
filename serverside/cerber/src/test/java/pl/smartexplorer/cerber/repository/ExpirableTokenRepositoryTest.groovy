package pl.smartexplorer.cerber.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator
import pl.smartexplorer.sev2Token.model.AbstractSev2Token
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@SpringBootTest
class ExpirableTokenRepositoryTest extends Specification {

    @Autowired
    ExpirableTokenRepository expirableTokenRepository

    AbstractSev2Token token
    TokenGenerator generator = new ExpirableTokenGenerator()

    void setup() {
        this.token = generator.generateToken(new Random().nextInt(1000000).toString(), "karoladmin", "193.33.22.44")
        println(token.toString())
    }

    def "save token entity SUCCESSFUL"() {
        when:
        expirableTokenRepository.save(token)

        then:
        assert expirableTokenRepository.findByUserId(token.getUserId()).isPresent()
        assert expirableTokenRepository.findByUsername(token.getUsername()).isPresent()
    }

    def "save token entity FAILED - NO PRESENT"() {
        when:
        expirableTokenRepository.save(token)

        then:
        !expirableTokenRepository.findByUserId("idnoexist").isPresent()
    }

    def "update token entity SUCCESSFUL"() {
        when:
        expirableTokenRepository.save(token)
        Sev2TokenExpirable expirable = (Sev2TokenExpirable)token
        expirable.setIpAddress("11.11.11.11")
        expirableTokenRepository.update(expirable)

        then:
        Sev2TokenExpirable updatedToken = (Sev2TokenExpirable)expirableTokenRepository.findByUserId(token.getUserId()).get()
        assert updatedToken.getUsername() == expirable.getUsername()
        assert updatedToken.getIpAddress() == expirable.getIpAddress() //updatedToken has same params like previous version
        println(updatedToken)
    }

    void cleanup() {
        expirableTokenRepository.delete(token)
    }

}
