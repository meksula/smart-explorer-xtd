package pl.smartexplorer.cerber.security.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import pl.smartexplorer.cerber.model.user.AuthenticationType
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.UserRepository
import spock.lang.Specification

import java.time.LocalDateTime

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

    protected User user
    def username = "testuser"
    def password = "password"

    def setup() {
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

    def cleanup() {
        userRepository.delete(user)
    }

}