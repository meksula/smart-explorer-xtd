package pl.smartexplorer.scribe.core.registration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.scribe.core.registration.builders.RegistrationBuilder;
import pl.smartexplorer.scribe.core.registration.builders.RegistrationBuilderTypes;
import pl.smartexplorer.scribe.core.registration.dto.CerberAuthDecisionRegistration;
import pl.smartexplorer.scribe.core.registration.dto.Registration;
import pl.smartexplorer.scribe.exception.ScribeAuthenticationException;
import pl.smartexplorer.scribe.model.User;
import pl.smartexplorer.scribe.services.mailer.model.MailTarget;
import pl.smartexplorer.scribe.services.mailer.producer.MailerProducer;
import pl.smartexplorer.scribe.services.mailer.producer.ResourcesMailerProducer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author
 * Karol Meksuła
 * 03-11-2018
 * */

@Slf4j
@Service
public class RegistrationProcessProcessExecutorDefault extends RegistrationProcessExecutor<CerberAuthDecisionRegistration, Registration> {
    private RegistrationBuilder<User, Registration> registrationBuilder;
    private MailerProducer mailerProducer;
    private RestTemplate restTemplate;
    private MessageSource messageSource;

    @Value("${cerber.hostname}")
    private String cerberHost;

    @Value("${registration.template}")
    private String registrationTemplate;

    public RegistrationProcessProcessExecutorDefault(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.registrationBuilder = (RegistrationBuilder<User, Registration>) RegistrationBuilderTypes.PRIMARY.produce();
        this.restTemplate = new RestTemplate();
    }

    @Autowired
    public void setMailerProducer(MailerProducer mailerProducer) {
        this.mailerProducer = mailerProducer;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CerberAuthDecisionRegistration execute(Registration data) {
        if (registrationBuilder == null) {
            final String MSG = "RegistrationBuilder not set. Inject RegistrationBuilder by setter to correct working this service.";
            log.error(MSG);
            throw new ScribeAuthenticationException(MSG);
        }

        User user = registrationBuilder.mapToCerberRequiredObject(data);
        CerberAuthDecisionRegistration cadr = databaseServiceRequest(user);

        if (cadr.isDecision())
            putMessageToBroker(cadr);

        return cadr;
    }

    @Override
    protected CerberAuthDecisionRegistration databaseServiceRequest(Object requestBody) {
        final String URI = requestAddress();

        try {
            log.info("Execute registration request to: " + URI);
            return restTemplate.postForEntity(URI, requestBody, CerberAuthDecisionRegistration.class).getBody();
        } catch (Exception ex) {
            log.error("Cannot execute registration request to Cerber. Check connection and Cerber status.");
            CerberAuthDecisionRegistration cadr = new CerberAuthDecisionRegistration();
            cadr.setDecision(false);
            cadr.setMessage("Some connection error occured. Please retry for few minutes.");
            return cadr;
        }

    }

    private String requestAddress() {
        if (isNull(cerberHost))
            throw new IllegalArgumentException("Cerber hostname is null! Set this up before execute any requests.");

        return cerberHost.concat("/api/v2/user/registration");
    }

    @Override
    protected boolean putMessageToBroker(CerberAuthDecisionRegistration cadr) {
        MailTarget mailTarget = MailTarget.builder()
                .targetEmail(cadr.getUser().getEmail())
                .username(cadr.getUsername())
                .mailTitle(messageSource.getMessage("registration.mail.title", null, Locale.getDefault()))
                .preparedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        log.info("Messages put to mailing queue");
        return mailerProducer.putToQueue(registrationTemplate, mailTarget, mapMailProperties(cadr));
    }

    private Map<String, String> mapMailProperties(CerberAuthDecisionRegistration cadr) {
        Map<String, String> properties = new HashMap<>();
        properties.put("verificationUuid", cadr.getVerificationUuid());
        properties.put("username", cadr.getUsername());
        properties.put("userId", cadr.getUser().getUserId());
        properties.put("verificationLink", linkBuild(cadr.getUser().getUserId(), cadr.getVerificationUuid()));
        return properties;
    }

    private String linkBuild(final String userId, final String verificationUuid) {
        return new StringBuilder()
                .append(cerberHost)
                .append("/api/v2/user/registration/verification/")
                .append(userId)
                .append("/")
                .append(verificationUuid)
                .toString()
                .trim();
    }

}
