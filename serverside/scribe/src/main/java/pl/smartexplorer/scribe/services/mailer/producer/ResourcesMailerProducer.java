package pl.smartexplorer.scribe.services.mailer.producer;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 *
 * This abstract class represents fundamental contract of more concrete Mailing service.
 * In constructor we must enter path to directory with mail templates.
 * Directory may be empty, by using method of Mailer it is possible to add specific templates with various formats.
 * */

public abstract class ResourcesMailerProducer implements MailerProducer {
    public String pathToTemplates;

    protected abstract Object prepareModel(Map<String, String> properties);
}
