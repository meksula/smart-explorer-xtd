package pl.smartexplorer.scribe.services.mailer.templates;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

public abstract class TemplatesManager {
    private String pathToTemplates;

    public TemplatesManager(String pathToTemplates) {
        this.pathToTemplates = pathToTemplates;
    }

    /**
     * Method can be use to send to server new template from Mailer service Client.
     * This template for example in HTML format should be encoded by Base64 algorithm and sent to server.
     * */
    public abstract boolean saveTemplatePlain(String templatePlain, String templateName);

    /**
     * This method can be use to load mail template encoded by Base64 algorithm.
     * */
    public abstract byte[] loadTemplateBased64(String templateName);

    /**
     * This method is able to load template directly from file, for example XML or HTML.
     * */
    public abstract byte[] loadTemplatePlain(String templateName);
}
