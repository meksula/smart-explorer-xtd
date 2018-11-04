package pl.smartexplorer.scribe.services.mailer.producer;

import pl.smartexplorer.scribe.services.mailer.model.MailTarget;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 *
 * This is a one of main interfaces of Mailer service module.
 * This interface represents each Mail Producer at mailing service.
 * Each mail is pushes to queue and next consumes by MailerConsumer.
 * */
public interface MailerProducer {
    /**
     * @param properties in this map we can put values to display in HTML template
     * */
    boolean putToQueue(String templateName, MailTarget mailTarget, Map<String, String> properties);

    /**
     * @param template probably HTML template encoded by Base64 for example
     * */
    boolean addTemplate(String template, String templateName);
}

