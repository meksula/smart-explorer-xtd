package pl.smartexplorer.scribe.services.mailer.templates;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

public class HtmlTemplatesManager extends TemplatesManager {

    public HtmlTemplatesManager(String pathToTemplates) {
        super(pathToTemplates);
    }

    @Override
    public boolean saveTemplatePlain(String templatePlain, String templateName) {
        return false;
    }

    @Override
    public String loadTemplateBased64(String templateName) {
        return null;
    }

    @Override
    public String loadTemplatePlain(String templateName) {
        return null;
    }

}
