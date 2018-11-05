package pl.smartexplorer.scribe.services.mailer.broker.spec;

import lombok.Getter;
import pl.smartexplorer.scribe.services.mailer.broker.MailBroker;

import org.springframework.amqp.core.Queue;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

@Getter
public class SimpleMailBroker implements MailBroker {
    private Queue queue;
}
