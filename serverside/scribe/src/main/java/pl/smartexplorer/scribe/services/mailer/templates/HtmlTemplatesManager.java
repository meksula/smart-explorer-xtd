package pl.smartexplorer.scribe.services.mailer.templates;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.scribe.services.mailer.templates.injector.HtmlTemplateInjector;
import pl.smartexplorer.scribe.services.mailer.templates.injector.TemplatesInjector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

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
        return false;
    }

    @Override
    public byte[] loadTemplateBased64(String templateName) {
        return null;
    }

    @Override
    public byte[] loadTemplatePlain(final String templateName) {
        String template = "";

        return template.getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public byte[] loadTemplateAndInjectProperties(final String templatePath, final Map<String, String> properties) {
        try {
            String htmlTemplate = String.join("\n", Files.readAllLines(Paths.get(templatePath)));
            return injector.injectSingle(htmlTemplate, properties);
        } catch (IOException e) {
            log.error("Cannot read file from path: " + templatePath);
            return new byte[0];
        }

    }

}
