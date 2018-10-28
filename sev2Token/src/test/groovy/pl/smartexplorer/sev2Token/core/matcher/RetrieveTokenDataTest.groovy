package pl.smartexplorer.sev2Token.core.matcher

import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator
import pl.smartexplorer.sev2Token.core.generator.TokenGenerator
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 28-10-2018
 * */

class RetrieveTokenDataTest extends Specification {
    protected RetrieveTokenData retrieveTokenData
    protected TokenGenerator tokenGenerator
    protected def userId = "94824290841df2d2"
    protected def username = "testuser"

    def setup() {
        this.retrieveTokenData = new RetrieveTokenData()
        this.tokenGenerator = new ExpirableTokenGenerator()
    }

    def "retireve userId test"() {
        setup:"encode sev2token"
        def token = tokenGenerator.generateToken(userId, username, "192.32.23.44")
        def encodedToken = tokenGenerator.encodeToken(token)

        expect:
        def retrievedUserId = retrieveTokenData.extractParameter(encodedToken, RetrieveTokenData.TokenParams.USER_ID)
        assert retrievedUserId == userId
    }

    def "retrieve username test"() {
        setup:"encode sev2token"
        def token = tokenGenerator.generateToken(userId, username, "192.32.23.44")
        def encodedToken = tokenGenerator.encodeToken(token)

        expect:
        def retrievedUsername = retrieveTokenData.extractParameter(encodedToken, RetrieveTokenData.TokenParams.USERNAME)
        assert retrievedUsername == username
    }

}
