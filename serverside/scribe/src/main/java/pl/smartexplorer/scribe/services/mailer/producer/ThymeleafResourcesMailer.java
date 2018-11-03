package pl.smartexplorer.scribe.services.mailer.producer;

import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.services.mailer.model.MailTarget;

import java.util.Map;

@Service
public class ThymeleafResourcesMailer extends ResourcesMailerProducer {

    //TODO
    private String pathToTemplates;

    @Override
    public boolean putToQueue(String templateName, MailTarget mailTarget, Map<String, String> properties) {
        return false;
    }

    @Override
    public Map<String, String> addTemplate(String template) {
        return null;
    }

    @Override
    protected Object prepareModel(Map<String, String> properties) {
        return null;
    }
}
