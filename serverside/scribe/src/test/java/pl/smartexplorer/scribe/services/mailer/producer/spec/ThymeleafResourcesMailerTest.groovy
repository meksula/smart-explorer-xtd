package pl.smartexplorer.scribe.services.mailer.producer.spec

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.scribe.services.mailer.model.MailTarget
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author
 * Karol Meksuła
 * 05-11-2018
 * */

@SpringBootTest
class ThymeleafResourcesMailerTest extends Specification {

    @Autowired
    private ThymeleafResourcesMailer mailer


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

}
