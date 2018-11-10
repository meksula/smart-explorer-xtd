package pl.smartexplorer.scribe.services.mailer.consumer.jms;

import pl.smartexplorer.scribe.services.mailer.model.MailPayloadWrapper;

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 * */

public interface MailSender {
    void sendMessage(MailPayloadWrapper wrapper);
}
