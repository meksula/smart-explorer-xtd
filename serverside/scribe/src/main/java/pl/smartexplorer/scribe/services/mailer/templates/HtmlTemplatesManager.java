package pl.smartexplorer.scribe.services.mailer.templates;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.scribe.services.mailer.templates.injector.HtmlTemplateInjector;
import pl.smartexplorer.scribe.services.mailer.templates.injector.TemplatesInjector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Slf4j
public class HtmlTemplatesManager extends TemplatesManager {
    private TemplatesInjector injector;

    public HtmlTemplatesManager(String pathToTemplates) {
        super(pathToTemplates);
        this.injector = new HtmlTemplateInjector();
    }

    @Override
    public boolean saveTemplatePlain(String templatePlain, String templateName) {
        //TODO
        return false;
    }

    @Override
    public byte[] loadTemplateBased64(String templateName) {
        //TODO
        return null;
    }

    @Override
    public byte[] loadTemplatePlain(final String templateName) {
        String template = "";

        return template.getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public byte[] loadTemplateAndInjectProperties(final String templatePath, final Map<String, String> properties) {
        if (isNull(templatePath) || isNull(properties)) {
            log.error("Cannot load template because one ore more required arguments are null");
            return new byte[0];
        }

        try {
            String htmlTemplate = readStringFromFilePath(templatePath);
            return injector.injectSingle(htmlTemplate, properties);
        } catch (IOException e) {
            log.error("Cannot read file from path: " + templatePath);
            return new byte[0];
        }

    }

    private String readStringFromFilePath(final String pathToTemplate) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(pathToTemplate)));
    }

    @Override
    public Map<String, Class> listRequiredProperties(String completeTemplatePath) {
        try {
            System.out.println(completeTemplatePath);
            return injector.listAllRequiredProperties(readStringFromFilePath(completeTemplatePath));
        } catch (IOException ex) {
            log.error("It seems that this path is malformed: " + completeTemplatePath);
            return Collections.emptyMap();
        }
    }

}
