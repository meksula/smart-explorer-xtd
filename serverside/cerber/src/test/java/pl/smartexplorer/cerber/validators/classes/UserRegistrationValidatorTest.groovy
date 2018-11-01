package pl.smartexplorer.cerber.validators.classes

import org.mockito.*
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.TokenRepository
import pl.smartexplorer.cerber.repository.UserRepository
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

class UserRegistrationValidatorTest extends Specification {
    private UserRegistrationValidator validator
    private def username = "freshuser"
    private def userId = "22392kd9"
    private User fresh

    @Mock
    private UserRepository userRepository

    @Mock
    private TokenRepository tokenRepository

    def setup() {
        MockitoAnnotations.initMocks(this)

        this.fresh = new User()
        fresh.setUsername(username)
        fresh.setUserId(userId)

        this.validator = new UserRegistrationValidator(userRepository, tokenRepository)
    }

    def "validator should ALLOW adding user [token and user exist] "() {
        setup:
        Mockito.when(userRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(null))
        Mockito.when(tokenRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(null))

        expect:
        assert validator.isAbleToRegister(fresh)
    }

    def "valiator should NOT ALLOW adding user [user exit but token is not]"() {
        setup:
        Mockito.when(userRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(fresh))
        Mockito.when(tokenRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(null))

        expect:
        !validator.isAbleToRegister(fresh)
    }

    def "valiator should NOT ALLOW adding user [user not exist but token is"() {
        setup:
        def token = new Sev2TokenExpirable(fresh.getUserId(), fresh.getUsername())
        Mockito.when(userRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(null))
        Mockito.when(tokenRepository.findByUsername(fresh.getUsername())).thenReturn(Optional.ofNullable(token))

        expect:
        !validator.isAbleToRegister(fresh)
    }

}
