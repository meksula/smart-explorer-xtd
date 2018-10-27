package pl.smartexplorer.cerber.repository.id

import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

class UserIdGeneratorTest extends Specification {
    UserIdGenerator generator = new UserIdGenerator()

    def "generator test" () {
        expect:
        def id = generator.generateId("karoladmin")
        println(id)
    }

}
