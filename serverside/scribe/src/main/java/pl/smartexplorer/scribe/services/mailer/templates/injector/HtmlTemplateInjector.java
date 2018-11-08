package pl.smartexplorer.scribe.services.mailer.templates.injector;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

@Slf4j
public class HtmlTemplateInjector implements TemplatesInjector {
    private static final String VALUE = "\\{\\{.+\\}\\}";

    @Override
    public byte[] injectSingle(String templatePlainHtml, Map<String, String> properties) {
        if (isNull(templatePlainHtml)) {
            final String MSG = "Warn! Found replaceable variable amount are not equivalent with properties size!";
            log.info(MSG);
            throw new IllegalArgumentException(MSG);
        }

        String result = templatePlainHtml;
        List<String> matches = matches(templatePlainHtml);

        if (matches.size() != properties.size()) {
            log.info("Warn! Found replaceable variable amount are not equivalent with properties size!");
        }

        for (String var : matches) {
            String key = var.substring(2, var.length() - 2);
            String value = properties.get(key);

            if (value == null) {
                continue;
            }
            else {
                result = result.replace(var, value);
            }
        }

        return result.getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public Map<String, Class> listAllRequiredProperties(String plainTemplate) {
        Map<String, Class> properties = new HashMap<>();
        List<String> matchesVar = matches(plainTemplate);

        for (String var : matchesVar)
            properties.put(var.substring(2, var.length() - 2), var.getClass());

        return Collections.unmodifiableMap(properties);
    }

    private List<String> matches(final String templatePlainHtml) {
        Pattern pattern = Pattern.compile(VALUE);
        Matcher matcher = pattern.matcher(templatePlainHtml);
        List<String> values = new ArrayList<>();

        while (matcher.find()) {
            values.add(matcher.group());
        }

        return values;
    }

}
