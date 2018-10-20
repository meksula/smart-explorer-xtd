package pl.smartexplorer.scribe.configuration

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@SpringBootTest
class CustomRestTemplateImplTest extends Specification {
    private CustomRestTemplate customRestTemplate

    def setup() {
        this.customRestTemplate = new CustomRestTemplateImpl()
    }

    def "rest request should NOT throw exception"() {
        when:
        customRestTemplate.requestForUser("randomusername")

        then:
        noExceptionThrown()

    }

}
