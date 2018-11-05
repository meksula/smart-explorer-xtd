package pl.smartexplorer.scribe.services.mailer.broker;

import org.springframework.amqp.core.Queue;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

public interface MailBroker {
    Queue getQueue();
}
