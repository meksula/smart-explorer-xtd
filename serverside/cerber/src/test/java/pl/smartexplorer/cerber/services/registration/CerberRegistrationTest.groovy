package pl.smartexplorer.cerber.services.registration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.cerber.repository.TokenRepository
import pl.smartexplorer.cerber.repository.UserRepository
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@SpringBootTest
class CerberRegistrationTest extends Specification {

    @Autowired
    private UserRegistrator userRegistrator

    @Autowired
    private UserRepository userRepository

    @Autowired
    private JdbcTemplate jdbcTemplate

    private TokenRepository tokenRepository

    private User user

    void setup() {
        tokenRepository = new ExpirableTokenRepository(jdbcTemplate)
    }

    def "save user and token should go correctly"() {
        when:
        def user = UserRegistrationModel.buildUser()
        this.user = userRegistrator.registerUser(user, "192.393.23.33")

        then:
        assert userRepository.findById(this.user.getUserId()).isPresent()
        assert userRepository.findByUsername(this.user.getUsername()).isPresent()
        assert tokenRepository.findByUserId(this.user.getUserId()).isPresent()
        assert tokenRepository.findByUsername(this.user.getUsername()).isPresent()
    }

    def "[IDEMPOTENT METHOD TEST] save user and token should BE FAILED because just exist in database"() {
        when: '4 attempt to create user with the same credentials'
        def user = UserRegistrationModel.buildUser()
        def firstIterationUserId = ""
        def otherIterationsUserIds = []
        for (int i = 0; i <= 4; i++) {
            this.user = userRegistrator.registerUser(user, "192.393.23.33")
            if (i == 0)
                firstIterationUserId = this.user.getUserId()
            else
                otherIterationsUserIds.add(this.user.getUserId())
        }

        then: "user did not saved to database so userId didn't assign"
        assert userRepository.findById(firstIterationUserId)

        for(String id : otherIterationsUserIds) {
            !userRepository.findById(id).isPresent() //other user's id was rejected
        }
    }

    void cleanup() {
        if (user != null)
            userRepository.delete(user)

        tokenRepository.dropTable()
    }

}
