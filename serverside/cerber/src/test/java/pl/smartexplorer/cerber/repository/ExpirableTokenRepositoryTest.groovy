package pl.smartexplorer.cerber.repository

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 22-10-2018
 * */

@SpringBootTest
class ExpirableTokenRepositoryTest extends Specification {

    def ""() {
        expect:
        def i = 2
        i == 2
    }

}
