package pl.smartexplorer.scribe.services.mailer.producer.spec

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.scribe.services.mailer.consumer.MailConsumer
import pl.smartexplorer.scribe.services.mailer.model.MailTarget
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author
 * Karol Meksuła
 * 05-11-2018
 * */

@SpringBootTest
class HtmlResourcesMailerTest extends Specification {

    @Autowired
    private HtmlResourcesMailer mailer

    @Autowired
    private MailConsumer mailConsumer

    def 'attempt to send message to RabbitMq'() {
        setup:
        MailTarget mailTarget = new MailTarget.MailTargetBuilder()
                .firstName("Mikołaj")
                .lastName("Kopernik")
                .targetEmail("mailtestowy@gmail.com")
                .preparedDate(LocalDateTime.now().toString())
                .mailTitle("Testowy email")
                .build()

        Map<String, String> props = new HashMap<>()
        props.put("money", "24493zł")
        props.put("token", "32dm32dm3209dm3209md902d9239d290d,32d2")
        props.put("year", "2018")

        when:
        mailer.putToQueue("template name etc...", mailTarget, props)

        then:
        noExceptionThrown()
    }

    def "getResourcePath should not be null"() {
        expect:
        assert mailer.getResourcesPath() != null
    }

    def "complete put mail to queue method with complete path test"() {
        setup:
        String resource = this.getClass().getResource( '/templates/mail/test.html' ).path
        def target = new MailTarget("karol.meksula@onet.pl", "karoladmin", "Init message",
                "Karol", "Meksula", LocalDateTime.now().toString())
        Map<String, String> prop = new HashMap<>()
        prop.put("username", "karoladmin")
        prop.put("date", LocalDateTime.now().toString())
        prop.put("debt", "983")

        when:
        mailer.putToQueue(resource, target, prop)

        then:
        noExceptionThrown()
    }

}
