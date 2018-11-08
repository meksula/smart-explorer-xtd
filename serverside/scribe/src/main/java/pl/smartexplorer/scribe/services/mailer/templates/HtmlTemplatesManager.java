package pl.smartexplorer.scribe.services.mailer.templates;

import lombok.extern.slf4j.Slf4j;
import pl.smartexplorer.scribe.services.mailer.templates.injector.HtmlTemplateInjector;
import pl.smartexplorer.scribe.services.mailer.templates.injector.TemplatesInjector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
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
    private final static String MSG = "Cannot read file from path: ";

    public HtmlTemplatesManager(String pathToTemplates) {
        super(pathToTemplates);
        this.injector = new HtmlTemplateInjector();
    }

    @Override
    public boolean saveTemplatePlain(String path, String templatePlain, String templateName) {
        try {
            final String COMPLETE_PATH = path.concat(templateName);
            File file = new File(COMPLETE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            String based = Base64.getEncoder().encodeToString(templatePlain.getBytes());
            bufferedWriter.write(based);
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            log.error("Something went wrong and IO operation was end with failure");
            return false;
        }

    }

    @Override
    public byte[] loadTemplateBased64(String pathToTemplate) {
        try {
            return Base64.getDecoder().decode(readStringFromFilePath(pathToTemplate));
        } catch (IOException e) {
            log.error(MSG + pathToTemplate);
            return new byte[0];
        }

    }

    @Override
    public byte[] loadTemplatePlain(final String templateName) {
        String template = "";
        try {
            template = readStringFromFilePath(templateName);
        } catch (IOException e) {
            log.error(MSG + templateName);
        }

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
            log.error(MSG + templatePath);
            return new byte[0];
        }

    }

    private String readStringFromFilePath(final String pathToTemplate) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(pathToTemplate)));
    }

    @Override
    public Map<String, Class> listRequiredProperties(String completeTemplatePath) {
        try {
            return injector.listAllRequiredProperties(readStringFromFilePath(completeTemplatePath));
        } catch (IOException ex) {
            log.error("It seems that this path is malformed: " + completeTemplatePath);
            return Collections.emptyMap();
        }
    }

}
