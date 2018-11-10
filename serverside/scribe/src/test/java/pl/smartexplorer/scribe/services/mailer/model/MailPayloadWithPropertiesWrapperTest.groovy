package pl.smartexplorer.scribe.services.mailer.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * @author
 * Karol Meksuła
 * 10-11-2018
 *
 * Write and read json test
 * */
class MailPayloadWithPropertiesWrapperTest extends Specification {
    private MailPayloadWithPropertiesWrapper wrapper
    private ObjectMapper mapper

    def setup() {
        this.wrapper = new MailPayloadWithPropertiesWrapper()
        this.mapper = new ObjectMapper()
    }

    def 'write and read json of wrapper object'() {
        when:
        Map<String, String> props = new HashMap<>()
        props.put("username", "karoladmin")
        props.put("email", "karol.meksula@onet.pl")
        wrapper.setProperties(props)
        def json = mapper.writeValueAsString(wrapper)
        println(json)

        then:
        def wrapperReaded = mapper.readValue(json, MailPayloadWithPropertiesWrapper.class)
        assert wrapperReaded.getProperties().get("username") == "karoladmin"
        assert wrapperReaded.getProperties().get("email") == "karol.meksula@onet.pl"
    }

}
