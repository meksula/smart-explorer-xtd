package pl.smartexplorer.scribe.services.mailer.broker.spec;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.scribe.services.mailer.broker.MailBroker;
import org.springframework.amqp.core.Queue;


/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

@Getter
@Slf4j
public class RabbitMailBroker implements MailBroker {
    private final String QUEUE_NAME = "smartexplorer-mailing";
    private Queue queue;

    public RabbitMailBroker() {
        this.queue = new Queue(QUEUE_NAME);
    }

}
