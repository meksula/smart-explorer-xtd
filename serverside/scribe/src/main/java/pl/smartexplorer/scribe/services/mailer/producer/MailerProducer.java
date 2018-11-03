package pl.smartexplorer.scribe.services.mailer.producer;

import pl.smartexplorer.scribe.services.mailer.model.MailTarget;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 *
 * This is a main interface of Mailer service module.
 * Every class must implements this interface.
 * */
public interface MailerProducer {
    /**
     * @param properties in this map we can put values to display in HTML template
     * */
    boolean putToQueue(String templateName, MailTarget mailTarget, Map<String, String> properties);

    /**
     * @param template probably HTML template encoded by Base64 for example
     * */
    Map<String, String> addTemplate(String template);
}

