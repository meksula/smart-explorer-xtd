package pl.smartexplorer.scribe.core.registration.builders

import com.fasterxml.jackson.databind.ObjectMapper
import pl.smartexplorer.scribe.core.registration.RegistrationPrepared
import pl.smartexplorer.scribe.core.registration.dto.Registration
import pl.smartexplorer.scribe.model.User
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

class PrimaryRegistrationBuilderTest extends Specification {
    private PrimaryRegistrationBuilder registrationBuilder = new PrimaryRegistrationBuilder()

    def "should correctly maps data to User.class"() {
        given:
        Registration registration = new RegistrationPrepared().prepare()
        println(new ObjectMapper().writeValueAsString(registration))

        when:
        User user = registrationBuilder.mapToCerberRequiredObject(registration)

        then:
        user.getEncryptedPassword().length() > 15
        def json = new ObjectMapper().writeValueAsString(user)
        println(json)
    }

}
