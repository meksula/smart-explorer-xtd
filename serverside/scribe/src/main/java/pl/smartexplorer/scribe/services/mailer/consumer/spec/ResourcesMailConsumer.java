package pl.smartexplorer.scribe.services.mailer.consumer.spec;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.services.mailer.broker.spec.RabbitMailBroker;
import pl.smartexplorer.scribe.services.mailer.consumer.MailConsumer;
import pl.smartexplorer.scribe.services.mailer.consumer.jms.MailSender;
import pl.smartexplorer.scribe.services.mailer.model.MailPayloadWrapper;

import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Slf4j
@Service
@AllArgsConstructor
@RabbitListener(queues = RabbitMailBroker.QUEUE_NAME)
public class ResourcesMailConsumer implements MailConsumer {
    private MailSender mailSender;

    @RabbitHandler
    @Override
    public void consume(String message) {

        try {
            MailPayloadWrapper wrapper = new ObjectMapper().readValue(message, MailPayloadWrapper.class);
            mailSender.sendMessage(wrapper);
        } catch (IOException e) {
            log.error("Cannot read message: JSON may be malformed");
        }

    }

}
