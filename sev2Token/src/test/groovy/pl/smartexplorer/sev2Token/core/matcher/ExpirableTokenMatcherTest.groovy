package pl.smartexplorer.sev2Token.core.matcher

import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator
import pl.smartexplorer.sev2Token.exception.Sev2TokenException
import spock.lang.Specification

import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * @author
 * Karol Meksuła
 * 13-10-2018
 * */

class ExpirableTokenMatcherTest extends Specification {
    private ExpirableTokenMatcher tokenMatcher = new ExpirableTokenMatcher(120)

    def "time between two LocalDateTime objects test"() {
        setup:
        LocalDateTime oneHourBefore = LocalDateTime.now().minusHours(1)
        long minutes = ChronoUnit.MINUTES.between(oneHourBefore, LocalDateTime.now())

        expect:
        minutes == 60
    }

    def "split token and extract date regex test"() {
        given:
        def tokenDecoded = "{38292+karoladmin+EXPIRABLE+false+2018-10-13T21:31:40.558+40b5ee4d-24d6-4c63-a2c8-dd3fc9df2d10}"
        String[] splited = tokenDecoded.split("\\+")

        expect:
        LocalDateTime dateTime = LocalDateTime.parse(splited[4])
        dateTime != null
    }

    def "isTokenExpired test should throw exception"() {
        given: 'token with error in date'
        def tokenDecoded = "{38292+karoladmin+EXPIRABLE+false+201-10-13T21:31:40.558+40b5ee4d-24d6-4c63-a2c8-dd3fc9df2d10}"
        def bytes = Base64.getEncoder().encodeToString(tokenDecoded.getBytes())
        def tokenEncrypted = new String(bytes as byte[], Charset.forName("UTF-8"))

        when:
        tokenMatcher.isTokenExpired(tokenEncrypted)

        then:
        thrown(Sev2TokenException.class)
    }

    def "isTokenExpired test : expired - NO ACCESS"() {
        given:
        def tokenDecoded = "{38292+karoladmin+EXPIRABLE+false+2018-10-12T21:31:40.558+40b5ee4d-24d6-4c63-a2c8-dd3fc9df2d10}"
        def bytes = Base64.getEncoder().encodeToString(tokenDecoded.getBytes())
        def tokenEncrypted = new String(bytes as byte[], Charset.forName("UTF-8"))

        expect: "should return `true` because token is expired"
        tokenMatcher.isTokenExpired(tokenEncrypted)
    }

    def "isTokenExpired test : NO expired - ACCESS SUCCESS"() {
        given:
        ExpirableTokenGenerator tokenGenerator = new ExpirableTokenGenerator()
        def freshToken = tokenGenerator.generateToken("92424", "karoladmin")
        def encodedToken = tokenGenerator.encodeToken(freshToken)

        expect: "should return `false` because token is not expired"
        !tokenMatcher.isTokenExpired(encodedToken)
    }

    def "allowAccess : ACCESS"() {
        ExpirableTokenGenerator tokenGenerator = new ExpirableTokenGenerator()
        def freshToken = tokenGenerator.generateToken("92424", "karoladmin")
        def encodedToken = tokenGenerator.encodeToken(freshToken)

        expect:
        tokenMatcher.allowAccess(encodedToken, encodedToken)
    }

    def "allowAccess : ATTEMPT_FAILED"() {
        ExpirableTokenGenerator tokenGenerator = new ExpirableTokenGenerator()
        def freshToken = tokenGenerator.generateToken("92424", "karoladmin")
        def encodedToken = tokenGenerator.encodeToken(freshToken)

        def freshToken2 = tokenGenerator.generateToken("92424", "karoladmin")
        def encodedToken2 = tokenGenerator.encodeToken(freshToken2)

        expect:
        !tokenMatcher.allowAccess(encodedToken, encodedToken2)
    }

}
