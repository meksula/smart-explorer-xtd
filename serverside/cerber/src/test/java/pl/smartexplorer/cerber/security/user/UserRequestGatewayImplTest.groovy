package pl.smartexplorer.cerber.security.user

import org.springframework.jdbc.core.JdbcTemplate
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import spock.lang.Ignore
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import pl.smartexplorer.cerber.dto.CerberAuthDecission
import pl.smartexplorer.cerber.dto.TokenEstablishData
import pl.smartexplorer.cerber.model.user.AuthenticationType
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.TokenRepository
import pl.smartexplorer.cerber.repository.UserRepository
import pl.smartexplorer.cerber.security.TokenManager
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import java.time.LocalDateTime

import static org.mockito.Mockito.*

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@SpringBootTest
class UserRequestGatewayImplTest extends Specification {

    @Autowired
    protected UserRequestGateway userRequestGateway

    @Autowired
    protected UserRepository userRepository

    @Autowired
    protected PasswordEncoder passwordEncoder

    @Autowired
    protected TokenManager tokenManager

    @Autowired
    protected TokenRepository tokenRepository

    @Mock
    protected HttpServletRequest request

    protected User user
    def username = "testuser"
    def password = "password"

    def setup() {
        validateMockitoUsage()

        user = new User()
        user.setUserId("93kd932d4")
        user.setAuthenticationType(AuthenticationType.SCRIBE.name())
        user.setSocialServiceId("203kd32d023")
        user.setSocialUsername("426264179650124")
        user.setUsername(username)
        user.setEncryptedPassword(passwordEncoder.encode(password))
        user.setEmail("testmail@gmail.com")
        user.setJoinDate(LocalDateTime.now().toString())
        user.setAccountNonLocked(true)
        user.setAccountNonExpired(true)
        user.setCredentialsNonExpired(true)
        user.setEnabled(true)

        when(request.getRemoteAddr()).thenReturn("192.168.0.1")
    }

    def "userRequestGateway should ALLOW return user entity test"() {
        setup: 'save user'
        userRepository.save(user)

        when:
        def userReceived = userRequestGateway.allowReturnUserEntity(username, password)

        then:
        userReceived != null
        userReceived.getUsername() == username
        userReceived.getSocialUsername() == "426264179650124"
    }

    def "userRequestGateway should NOT allow return user entity test"() {
        setup: 'save user'
        userRepository.save(user)

        when:
        def userReceived = userRequestGateway.allowReturnUserEntity(username, "password1")

        then:
        userReceived == null
    }

    @Ignore
    def "request mock behavior test" () {
        setup:
        def ip = "192.168.0.1"
        when(request.getRemoteAddr()).thenReturn(ip)

        when:
        def result = request.getRemoteAddr()
        println(result)

        then:
        result == ip
        verify(request, times(1))
    }

    def "userRequestGateway should ALLOW return user and should assign new sev2token entity test"() {
        setup:
        userRepository.save(user)
        //except 'User.class' object to this method required is sev2token entity saved in database
        tokenManager.generateTokenAndSave(buildTokenData(user.getUserId(), user.getUsername(), "192.168.0.1"))

        when:
        CerberAuthDecission decission = userRequestGateway.allowReturnCerberDesision(username, password, request)

        then:
        assert decission.isDecision()
        assert decission.getSev2token() != null
        assert decission.getUser() != null
        assert decission.getUser().getUsername() == username
        assert decission.getUser().getEmail() == "testmail@gmail.com"

        /**
         * Why not using deleteByUserId() ???
         * Because I do not know userId, because it is establishing in user registration. I've got only fake userId
         * */
        cleanup:
        tokenRepository.dropTable()
    }

    @Autowired
    protected JdbcTemplate jdbcTemplate

    def "userRequestGateway should NOT ALLOW return user and should NOT assign new sev2token entity test"() {
        setup:
        tokenRepository = new ExpirableTokenRepository(jdbcTemplate)
        userRepository.save(user)
        when(request.getRemoteAddr()).thenReturn("192.168.0.1")

        when:
        CerberAuthDecission decission = userRequestGateway.allowReturnCerberDesision(username, password, request)

        then:
        verify(request, times(1))

        and:
        !decission.isDecision()
        assert decission.getSev2token() == null
        assert decission.getUser() != null
        assert decission.getMessage() == "User retrieved successfully but some error occured while sev2token update"
    }

    protected TokenEstablishData buildTokenData(String userId, String username, String ip) {
        TokenEstablishData tokenEstablishData = new TokenEstablishData()
        tokenEstablishData.setUserId(userId)
        tokenEstablishData.setUsername(username)
        tokenEstablishData.setIpAddress(ip)
        return tokenEstablishData
    }

    def cleanup() {
        if (user !=null) userRepository.delete(user)
    }

}