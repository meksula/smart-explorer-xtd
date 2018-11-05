package pl.smartexplorer.scribe.services.mailer.templates;

import java.nio.charset.Charset;

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
    public byte[] loadTemplateBased64(String templateName) {
        return null;
    }

    @Override
    public byte[] loadTemplatePlain(String templateName) {
        String tmp = "<!DOCTYPE html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Login</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "\n" +
                "<body>\n" +
                "<div th:fragment=\"content\">\n" +
                "    <form name=\"f\" th:action=\"@{/login}\" method=\"post\">\n" +
                "        <fieldset>\n" +
                "            <legend>Please Login</legend>\n" +
                "            <div th:if=\"${param.error}\" class=\"alert alert-error\">\n" +
                "                Invalid username and password.\n" +
                "            </div>\n" +
                "            <div th:if=\"${param.logout}\" class=\"alert alert-success\">\n" +
                "                You have been logged out.\n" +
                "            </div>\n" +
                "            <label for=\"username\">Username</label>\n" +
                "            <input type=\"text\" id=\"username\" name=\"username\"/>\n" +
                "            <label for=\"password\">Password</label>\n" +
                "            <input type=\"password\" id=\"password\" name=\"password\"/>\n" +
                "            <div class=\"form-actions\">\n" +
                "                <button type=\"submit\" class=\"btn\">Log in</button>\n" +
                "            </div>\n" +
                "        </fieldset>\n" +
                "    </form>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n" +
                "</body>\n" +
                "</html>";

        return tmp.getBytes(Charset.forName("UTF-8"));
    }

}
