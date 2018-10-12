package pl.smartexplorer.sev2Token.core.generator

import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
import spock.lang.Specification

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author
 * Karol Meksuła
 * 12-10-2018
 * */

class ExpirableTokenGeneratorTest extends Specification {
    private ExpirableTokenGenerator tokenGenerator
    private final USER_ID = 18813183l
    private final String USERNAME = "Mikolaj.kopernik"

    def setup() {
        this.tokenGenerator = new ExpirableTokenGenerator()
    }

    def "token generation test"() {
        when:
        def token = tokenGenerator.generateToken(String.valueOf(USER_ID), USERNAME)
        def tokenString = tokenGenerator.encodeToken(token)

        then:
        token instanceof Sev2TokenExpirable
        tokenString.length() == 140
    }

    def "regex test"() {
        given:
        def tokenAsString = "{18813183+Mikolaj.kopernik+EXPIRABLE+false+2018-10-12T23:40:07.733+9bb7ae69-91b4-424f-835e-54459d401105}"

        when:
        Pattern pattern = Pattern.compile("\\{{1}[0-9]+\\+[a-zA-Z0-9.-=]+\\+[A-Z]+\\+[falsetrue]+\\+[-.:T0-9]+\\+" +
                "[-0-9a-zA-Z]+[}]{1}")
        Matcher matcher = pattern.matcher(tokenAsString)

        then:
        matcher.matches()
    }

}
