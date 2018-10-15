package pl.smartexplorer.sev2Token.core.data.exec

import pl.smartexplorer.sev2Token.core.data.DatabasesAvailable
import pl.smartexplorer.sev2Token.exception.Sev2TokenException
import pl.smartexplorer.sev2Token.model.expirable.Sev2TokenExpirable
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 15-10-2018
 * */

class JdbcExecutorFactoryTest extends Specification {
    private JdbcExecutorFactory factory = new JdbcExecutorFactory(DatabasesAvailable.MYSQL)

    def "factory should return correct object"() {
        expect:
        def result = factory.getJdbcExecutor(Sev2TokenExpirable.class, "some address", "username", "password")
        result != null
        result instanceof Sev2TokenExpirableJdbcExecutor
    }

    def "factory should not recognize type of object and throw exception"() {
        when: "ERROR: class not supported"
        factory.getJdbcExecutor(Sev2TokenExpirableJdbcExecutor.class, "some address", "username", "password")

        then:
        thrown(Sev2TokenException)
    }

}
