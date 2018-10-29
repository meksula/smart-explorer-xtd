package pl.smartexplorer.scribe.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

class UserTest extends Specification {
    private User user = new User()

    def "JSON stringify test" () {
        expect:
        def stringified = new ObjectMapper().writeValueAsString(user)
        println(stringified)
    }

}
