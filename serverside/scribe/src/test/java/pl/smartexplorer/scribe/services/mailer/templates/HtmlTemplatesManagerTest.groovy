package pl.smartexplorer.scribe.services.mailer.templates

import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

class HtmlTemplatesManagerTest extends Specification {
    HtmlTemplatesManager templatesManager = new HtmlTemplatesManager()

    def "load template from directory test" () {
        when:
        def bytes = templatesManager.loadTemplateAndInjectProperties("/home/karol/templates/welcome_mail.html", new HashMap<String, String>())

        then:
        bytes != null
    }

}
