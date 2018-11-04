package pl.smartexplorer.scribe.services.mailer.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.smartexplorer.scribe.services.mailer.model.MailTarget;
import pl.smartexplorer.scribe.services.mailer.templates.HtmlTemplatesManager;
import pl.smartexplorer.scribe.services.mailer.templates.TemplatesManager;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

@Slf4j
@Service
public class ThymeleafResourcesMailer extends ResourcesMailerProducer {
    private TemplatesManager templatesManager;

    @Value("${mail.templates.dir}")
    private String pathToTemplates;

    public ThymeleafResourcesMailer() {
        this.templatesManager = new HtmlTemplatesManager(pathToTemplates);
    }

    @Override
    public boolean putToQueue(String templateName, MailTarget mailTarget, Map<String, String> properties) {
        //prepare connection with broker
        //before prepare equivalent bean
        return false;
    }

    @Override
    public boolean addTemplate(String template, String templateName) {
        return templatesManager.saveTemplatePlain(template, templateName);
    }

    @Override
    protected Object prepareModel(Map<String, String> properties) {
        //prepare model for thymeleaf template - values that are mentioned in template
        return null;
    }

    @Override
    public String getResourcesPath() {
        return pathToTemplates;
    }

}
