package pl.smartexplorer.scribe.services.mailer.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.scribe.services.mailer.ResourcesMailer;
import pl.smartexplorer.scribe.services.mailer.model.MailTarget;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 *
 * Specific implementation of ResourcesMailer for Thymeleaf web engine
 * */

@Slf4j
public class ThymeleafResourcesMailer extends ResourcesMailer {
    public ThymeleafResourcesMailer(String pathToTemplates) {
        super(pathToTemplates);
    }

    @Override
    protected Object prepareModel(Map<String, String> properties) {
        return null;
    }

    @Override
    public void sendMail(String templateName, MailTarget mailTarget, Map<String, String> properties) {

    }

    @Override
    public Map<String, String> addTemplate(String template) {
        return null;
    }
}
