package pl.smartexplorer.cerber.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.cerber.dto.TokenEstablishData
import spock.lang.Specification


/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@SpringBootTest
class MainTokenManagerTest extends Specification {

    @Autowired
    MainTokenManager tokenManager

    TokenEstablishData tokenEstablishData
    TokenEstablishData invalidEstablishData

    def validUserId = "2942432"
    def validUsername = "karoladmin"
    def validIpAddress = "192.392.292.22"

    def setup() {
        this.tokenEstablishData = new TokenEstablishData(validUserId, validUsername, validIpAddress)
        this.invalidEstablishData = new TokenEstablishData(validUserId, validUsername, "129922993")
    }

    def "MainTokenManager should correctly pass VALID value"() {
        expect:
        def result = tokenManager.generateTokenAndSave(tokenEstablishData)
        println(result)
    }


}
