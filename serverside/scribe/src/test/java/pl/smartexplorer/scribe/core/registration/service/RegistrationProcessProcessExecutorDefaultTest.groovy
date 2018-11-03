package pl.smartexplorer.scribe.core.registration.service

import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import pl.smartexplorer.scribe.core.registration.RegistrationPrepared
import pl.smartexplorer.scribe.core.registration.dto.CerberAuthDecisionRegistration
import pl.smartexplorer.scribe.core.registration.dto.Registration
import pl.smartexplorer.scribe.model.User
import pl.smartexplorer.scribe.services.mailer.model.MailTarget

import static org.mockito.ArgumentMatchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.smartexplorer.scribe.services.mailer.producer.MailerProducer
import spock.lang.Specification

import static org.mockito.Mockito.times

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

@SpringBootTest
class RegistrationProcessProcessExecutorDefaultTest extends Specification {

    @Autowired
    private RegistrationProcessProcessExecutorDefault executorDefault

    @Mock
    private MailerProducer mailerProducer

    @Mock
    private RestTemplate restTemplate

    private CerberAuthDecisionRegistration cadr

    def setup() {
        cadr = new CerberAuthDecisionRegistration()
        cadr.setDecision(true)
        cadr.setVerificationUuid("983jd293md3209md3209dm")
        cadr.setSev2token("29kd0329kd092d092d23d32d2")
        cadr.setMessage("Registered with success")
        cadr.setUser(new User())

        def entity = new ResponseEntity<?>()
        //entity.body = this.cadr

        Mockito.when(mailerProducer.putToQueue(anyObject(), anyObject(), anyObject())).thenReturn(true)
        Mockito.when(restTemplate.postForEntity(anyObject(), anyObject(), CerberAuthDecisionRegistration.class))
                .thenReturn(entity)
    }

    def "mock init test"() {
        setup:
        //Mockito.when(mailerProducer.putToQueue(any(String.class), any(MailTarget.class), any(Map.class))).thenReturn(true)
        executorDefault.setMailerProducer(mailerProducer)

        expect:
        mailerProducer != null
        assert mailerProducer.putToQueue("", new MailTarget.MailTargetBuilder().build(), new HashMap<String, String>())
    }

    def "registration method execute should be SUCCESSFUL test"() {
        setup:
        Registration registration = new RegistrationPrepared().prepare()
        executorDefault.setMailerProducer(this.mailerProducer)
        executorDefault.setRestTemplate(this.restTemplate)

        when:
        CerberAuthDecisionRegistration cadrReceived = executorDefault.execute(registration)

        then:
        this.cadr == cadrReceived
        Mockito.verify(mailerProducer.putToQueue(any(String.class), any(MailTarget.class), any(Map.class)), times(1))
        Mockito.verify(restTemplate.postForEntity(any(String.class), any(Object.class), CerberAuthDecisionRegistration.class), times(1))
    }

}




