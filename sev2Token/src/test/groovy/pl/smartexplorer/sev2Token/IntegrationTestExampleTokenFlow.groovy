package pl.smartexplorer.sev2Token

import pl.smartexplorer.sev2Token.fragmented.FragmentedTokenFacade
import pl.smartexplorer.sev2Token.fragmented.FragmentedTokenFacadeBasic
import pl.smartexplorer.sev2Token.model.Sev2TokenType
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

class IntegrationTestExampleTokenFlow extends Specification {
    FragmentedTokenFacade facadeBasic
    def username = "karoladmin"
    def userId = "39dm93d2"
    def ipAddress = "192.3.2.22"

    void setup() {
        this.facadeBasic = new FragmentedTokenFacadeBasic(Sev2TokenType.EXPIRABLE, 100)
    }

    def "example SUCCESS execution flow" () {
        when:
        def expirableToken = facadeBasic.generateToken(userId, username, ipAddress)
        println(expirableToken)

        then:
        def encodedToken = facadeBasic.encodeToken(expirableToken)
        println(encodedToken)

        !facadeBasic.isExpired(encodedToken)
        facadeBasic.allow(encodedToken, expirableToken)
    }

    def "example FAILED execution flow" () {
        when:
        def otherIpAddress = "192.10.102.02"
        def expirableToken = facadeBasic.generateToken(userId, username, ipAddress)
        println(expirableToken)

        def expirableTokenOther = facadeBasic.generateToken(userId, username, otherIpAddress)

        then:
        def encodedToken = facadeBasic.encodeToken(expirableToken)
        println(encodedToken)

        !facadeBasic.isExpired(encodedToken)
        !facadeBasic.allow(encodedToken, expirableTokenOther) //NOT ALLOW
    }

}
