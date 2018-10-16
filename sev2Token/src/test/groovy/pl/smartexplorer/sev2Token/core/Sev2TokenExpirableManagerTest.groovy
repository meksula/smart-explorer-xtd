package pl.smartexplorer.sev2Token.core

import pl.smartexplorer.sev2Token.exception.Sev2TokenException
import spock.lang.Specification

import java.nio.charset.Charset

/**
 * @author
 * Karol Meksuła
 * 13-10-2018
 * */

class Sev2TokenExpirableManagerTest extends Specification {
    private Sev2TokenExpirableManager sev2TokenExpirableManager

    def setup() {
       this.sev2TokenExpirableManager = new Sev2TokenExpirableManager()
    }

    def "generate() test - should throw exception"() {
        when:
        sev2TokenExpirableManager.generate("", "", "12.3m34.3224.33")

        then:
        thrown(Sev2TokenException.class)
    }

    def "generate() test - should throw exception - username should has min. 6 characters"() {
        when:
        sev2TokenExpirableManager.generate("343", "dk39d", "12.223.223.44")

        then:
        thrown(Sev2TokenException.class)
    }

    def "byte array comparison test"() {
        given: "encode some token for example user - this will be save to database"
        def encoded = sev2TokenExpirableManager.generate("38292", "karoladmin", "192.22.93.23")

        expect:
        byte[] decoded = Base64.getDecoder().decode(encoded)
        String clear = new String(decoded, Charset.forName("UTF-8"))
        println(clear)
    }

    def "generate() test - should CORRECTLY create new passwords"() {
        expect:
        def encoded = sev2TokenExpirableManager.generate(userId, username, "183.33.25.84")
        encoded.length() > 100
        def decoded = Base64.getDecoder().decode(encoded)
        print(encoded)
        print(decoded instanceof byte[])

        where:
        userId       | username
        "45433"      | "Kazik41"
        "3424244242" | "9j39dmd93"
        "3222665"    | "322dxd32"
    }

}
