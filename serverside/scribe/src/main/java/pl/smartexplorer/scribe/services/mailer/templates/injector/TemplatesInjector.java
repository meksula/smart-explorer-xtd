package pl.smartexplorer.scribe.services.mailer.templates.injector;

import java.util.Map;

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

public interface TemplatesInjector {
    byte[] injectSingle(String templatePlainHtml, Map<String, String> properties);
}
