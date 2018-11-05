package pl.smartexplorer.scribe.services.mailer.producer.spec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import pl.smartexplorer.scribe.services.mailer.broker.MailBroker;
import pl.smartexplorer.scribe.services.mailer.broker.MailBrokerFactory;
import pl.smartexplorer.scribe.services.mailer.model.MailTarget;
import pl.smartexplorer.scribe.services.mailer.producer.ResourcesMailerProducer;
import pl.smartexplorer.scribe.services.mailer.templates.HtmlTemplatesManager;
import pl.smartexplorer.scribe.services.mailer.templates.TemplatesManager;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

@Slf4j
@Service
public class ThymeleafResourcesMailer extends ResourcesMailerProducer {
    private TemplatesManager templatesManager;
    private RabbitTemplate rabbitTemplate;
    private MailBroker mailBroker;
    private ObjectMapper objectMapper;

    @Value("${mail.templates.dir}")
    private String pathToTemplates;

    public ThymeleafResourcesMailer(RabbitTemplate rabbitTemplate, MailBrokerFactory mailBrokerFactory) {
        this.templatesManager = new HtmlTemplatesManager(pathToTemplates);
        this.rabbitTemplate = rabbitTemplate;
        this.mailBroker = mailBrokerFactory.mailBroker();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public boolean putToQueue(String templateName, MailTarget mailTarget, Map<String, String> properties) {
        final String pathToTemplate = pathNegotiate(templateName);
        String templateEncoded = encodeTemplate(pathToTemplate);

        if (!isNull(mailTarget.getTargetEmail())) {
            try {
                rabbitTemplate.convertAndSend(mailBroker.getQueue().getName(), writeAsJson(templateEncoded, mailTarget, properties));
            } catch (JsonProcessingException e) {
                log.error("Cannot send message to broker because cannot parse objects to JSON = " + e.getClass());
            }

            log.info("Mail pushed to queue and now waiting for send to target.");
            return true;
        }

        log.info("Some values are not initialized or occured some other error while data to send mail was processing " +
                "and preparing to send.");
        return false;
    }

    @Override
    public boolean addTemplate(String template, String templateName) {
        return templatesManager.saveTemplatePlain(template, templateName);
    }

    @Override
    protected Object prepareModel(Map<String, String> properties) {
        Context context = new Context();
        properties.forEach(context::setVariable);
        return context;
    }

    @Override
    public String getResourcesPath() {
        return pathToTemplates;
    }

    private String pathNegotiate(final String templateName) {
        if (templateName.startsWith("*"))
            return pathToTemplates.concat(templateName.substring(1));
        else return templateName;
    }

    private String encodeTemplate(final String pathToTemplate) {
        byte[] templateBytes = loadTemplate(pathToTemplate);
        return Base64.getEncoder().encodeToString(templateBytes);
    }

    private byte[] loadTemplate(final String pathToTemplate) {
        return templatesManager.loadTemplatePlain(pathToTemplate);
    }

    private String writeAsJson(final String templateEncoded, MailTarget mailTarget, Map<String, String> properties) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                Collections.unmodifiableList(Arrays.asList(templateEncoded, mailTarget, properties)));
    }

}
