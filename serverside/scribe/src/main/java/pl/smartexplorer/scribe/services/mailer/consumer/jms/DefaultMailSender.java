package pl.smartexplorer.scribe.services.mailer.consumer.jms;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.services.mailer.model.MailPayloadWrapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 * */

@Slf4j
@Service
@AllArgsConstructor
public class DefaultMailSender implements MailSender {
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessage(MailPayloadWrapper wrapper) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            messageHelper.setSubject(wrapper.getMailTarget().getMailTitle());
            messageHelper.setTo(wrapper.getMailTarget().getTargetEmail());
            messageHelper.setText(plainHtmlDecode(wrapper.getTemplateEncoded().getTemplateBase64()), true);
            log.info("Mail sent correctly");
        } catch (MessagingException e) {
            log.error("MimeMessageHelper building went wrong: " + e.getCause());
        }

        javaMailSender.send(mimeMessage);
    }

    private String plainHtmlDecode(String base64template) {
        return new String(Base64.getDecoder().decode(base64template), Charset.forName("UTF-8"));
    }

}
