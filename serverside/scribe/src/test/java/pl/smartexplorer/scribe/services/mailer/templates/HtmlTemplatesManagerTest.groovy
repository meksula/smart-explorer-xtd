package pl.smartexplorer.scribe.services.mailer.templates

import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

class HtmlTemplatesManagerTest extends Specification {
    HtmlTemplatesManager templatesManager = new HtmlTemplatesManager()

    def "should not throw exception if template not exist and return empty byte array" () {
        when:
        def bytes = templatesManager.loadTemplateAndInjectProperties("/home/no-exist.html", new HashMap<String, String>())

        then:
        bytes != null
        assert bytes.length == 0
    }

    def "null map or null template should no be cause of exception"() {
        when:
        templatesManager.loadTemplateAndInjectProperties(null, null)

        then:
        noExceptionThrown()
    }

    def "should correctly load HTML template"() {
        setup:
        String resource = this.getClass().getResource( '/templates/mail/test.html' ).text

        expect:
        print(resource)
    }

    def "should correctly list all variable elements in template" () {
        setup:
        String resourcePath = this.getClass().getResource('/templates/mail/test.html').path

        expect:
        assert templatesManager.listRequiredProperties(resourcePath).size() == 3
    }

}
