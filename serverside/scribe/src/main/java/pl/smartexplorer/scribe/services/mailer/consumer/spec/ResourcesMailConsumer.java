package pl.smartexplorer.scribe.services.mailer.consumer.spec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.services.mailer.broker.spec.RabbitMailBroker;
import pl.smartexplorer.scribe.services.mailer.consumer.MailConsumer;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Slf4j
@Service
@RabbitListener(queues = RabbitMailBroker.QUEUE_NAME)
public class ResourcesMailConsumer implements MailConsumer {

    @RabbitHandler
    @Override
    public void consume(final String message) {
        System.out.println("Consumed: " + message);
    }

}
