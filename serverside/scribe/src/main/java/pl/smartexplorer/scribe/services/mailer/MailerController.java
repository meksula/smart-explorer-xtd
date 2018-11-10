package pl.smartexplorer.scribe.services.mailer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.scribe.services.mailer.model.ExternalMailPayloadWrapper;
import pl.smartexplorer.scribe.services.mailer.producer.MailerProducer;

/**
 * @author
 * Karol Meksuła
 * 05-11-2018
 * */

@Slf4j
@RestController
@RequestMapping("/api/v2/mail")
public class MailerController {
    private MailerProducer mailerProducer;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${mail.token}")
    private String mailToken;

    public MailerController(MailerProducer mailerProducer) {
        this.mailerProducer = mailerProducer;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestHeader String key, @RequestBody ExternalMailPayloadWrapper wrapper) {
        if (!passwordEncoder.matches(key, mailToken)) {
            log.warn("Unauthorized being tried to send mail. Attempt was filtered and rejected");
            return;
        }

        try {
            log.info("Received new mail request");
            mailerProducer.putToQueue(wrapper.getTemplateName(), wrapper.getMailTarget(), wrapper.getProperties());
        } catch (RuntimeException re) {
            log.error("Mail not sent");
        }

    }

}
