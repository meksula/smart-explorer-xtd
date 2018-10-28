package pl.smartexplorer.cerber.security.verify

import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.sev2Token.core.generator.ExpirableTokenGenerator
import pl.smartexplorer.sev2Token.model.AbstractSev2Token
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * @author
 * Karol Meksuła
 * 28-10-2018
 * */

@SpringBootTest
class DefaultExpirableTokenVerificatorTest extends Specification {
    final def HEADER = "sev2token"

    protected DefaultExpirableTokenVerificator tokenVerificator

    @Mock
    protected HttpServletRequest servletRequest

    @Mock
    protected ExpirableTokenRepository tokenRepository

    def userId = "19930811"
    protected AbstractSev2Token token
    protected String encodedToken
    protected String invalidEncodedToken

    def setup() {
        this.tokenVerificator = new DefaultExpirableTokenVerificator(tokenRepository)
        def tokenGen = new ExpirableTokenGenerator()
        this.token = tokenGen.generateToken(userId, "testuser", "19.203.223.3")
        this.encodedToken = tokenGen.encodeToken(token)
        this.invalidEncodedToken = encodedToken.substring(1)
    }

    def "should return true AUTHENTICATION SUCCESS test"() {
        setup:
        Mockito.when(tokenRepository.findByUserId(userId)).thenReturn(Optional.of(token))
        Mockito.when(servletRequest.getHeader(HEADER)).thenReturn(encodedToken)

        expect:
        assert tokenVerificator.verifyToken(servletRequest)
    }

    def "should return false AUTHENTICATION FAILED test"() {
        setup:
        Mockito.when(tokenRepository.findByUserId(userId)).thenReturn(Optional.of(token))
        Mockito.when(servletRequest.getHeader(HEADER)).thenReturn(invalidEncodedToken)

        expect:
        !tokenVerificator.verifyToken(servletRequest)
    }

}
