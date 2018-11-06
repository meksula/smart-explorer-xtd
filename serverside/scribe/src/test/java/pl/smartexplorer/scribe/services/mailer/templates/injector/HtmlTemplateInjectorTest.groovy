package pl.smartexplorer.scribe.services.mailer.templates.injector

import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 06-11-2018
 * */

class HtmlTemplateInjectorTest extends Specification {
    private HtmlTemplateInjector injector = new HtmlTemplateInjector()

    def template = "<!DOCTYPE html>\n" +
            "<html lang=\"pl\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Witamy w {{app}}</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\t<h2>Bardzo miło nam Cię gościć w naszej aplikacji! :)</h2>\n" +
            "\t<p>{{username}} mamy nadzieję, że będziemy się razem bardzo dobrze bawić!</p>\n" +
            "</body>\n" +
            "</html>"

    def "injector behaviour monitoring" () {
        setup:
        Map<String, String> prop = new HashMap<>()
        prop.put("username", "Karol")
        prop.put("app", "Smart Explorer")

        when:
        def bytes = injector.injectSingle(template, prop)

        then:
        new String(bytes) == "<!DOCTYPE html>\n" +
                "<html lang=\"pl\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Witamy w Smart Explorer</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h2>Bardzo miło nam Cię gościć w naszej aplikacji! :)</h2>\n" +
                "\t<p>Karol mamy nadzieję, że będziemy się razem bardzo dobrze bawić!</p>\n" +
                "</body>\n" +
                "</html>"
    }

    def "should not throw exception if map is empty test"() {
        setup: 'empty map'
        Map<String, String> prop = new HashMap<>()

        when:
        injector.injectSingle(template, prop)

        then:
        noExceptionThrown()
    }

    def "should throw exception if template is empty test"() {
        setup: 'empty template'
        Map<String, String> prop = new HashMap<>()

        when:
        injector.injectSingle(null, prop)

        then:
        thrown(IllegalArgumentException.class)
    }

}
