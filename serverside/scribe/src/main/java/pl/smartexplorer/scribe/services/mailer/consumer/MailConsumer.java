package pl.smartexplorer.scribe.services.mailer.consumer;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

public interface MailConsumer {
    String consume(String message);
}
