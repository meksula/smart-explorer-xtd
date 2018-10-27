package pl.smartexplorer.cerber.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.cerber.model.user.UserProfile
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 26-10-2018
 * */

@SpringBootTest
class UserProfileRepositoryTest extends Specification {

    @Autowired
    private UserProfileRepository repository
    private UserProfile userProfile
    def username = "karoladmin"

    void setup() {
        userProfile = new UserProfile()
        userProfile.setUsername(username)
    }

    def "repository custom querry test save, and find by username"() {
        setup:
        def saved = repository.save(userProfile)
        println(new ObjectMapper().writeValueAsString(saved))

        expect:
        assert repository.findById(saved.getUserProfileId()).isPresent()
    }

    void cleanup() {
        repository.delete(userProfile)
    }

}
