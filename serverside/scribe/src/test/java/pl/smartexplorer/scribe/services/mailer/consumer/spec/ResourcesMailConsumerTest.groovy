package pl.smartexplorer.scribe.services.mailer.consumer.spec

import com.fasterxml.jackson.databind.ObjectMapper
import pl.smartexplorer.scribe.services.mailer.model.MailPayloadWrapper
import spock.lang.Specification

/**
 * Experiment: decoding received JSON and execute mail send
 * */
class ResourcesMailConsumerTest extends Specification {
    private final String JSON_MAIL_RECEIVED = "{\"templateEncoded\":{\"templateBase64\":\"PCFET0NUWVBFIGh0bWw+CjxodG1sIG" +
            "xhbmc9InBsIiB4bWxuczp0aD0iaHR0cDovL3d3dy50aHltZWxlYWYub3JnIj4KPGhlYWQ+CiAgICA8bWV0YSBjaGFyc2V0PSJVVEYtOCI" +
            "+CiAgICA8dGl0bGU+UmVqZXN0cmFjamE8L3RpdGxlPgo8L2hlYWQ+Cjxib2R5PgogICAgPGgzPldlcnlmaWthY2phIHJlamVzdHJhY2p" +
            "pPC9oMz4KCTxwPmVsaW90YWxkZXJzb24yPC9wPgoJPHA+N2NiM2I3MDQtMzE2Yy00YzRlLThiMWItOWFmZmUwOTdlMzFlPC9wPgo8L2J" +
            "vZHk+CjwvaHRtbD4=\"},\"mailTarget\":{\"targetEmail\":\"mikolaj.kopernik@gmail.com\",\"username\":\"eliot" +
            "alderson2\",\"mailTitle\":\"Smart Explorer rejestracja\",\"firstName\":null,\"lastName\":null,\"prepared" +
            "Date\":\"2018-11-10 14:53:54\"}}"
    private ObjectMapper mapper

    def setup() {
        mapper = new ObjectMapper()
    }

    def 'read json test'() {
        when:
        MailPayloadWrapper wrapper = mapper.readValue(JSON_MAIL_RECEIVED, MailPayloadWrapper.class)

        then:
        assert wrapper.getTemplateEncoded() != null
        assert wrapper.getMailTarget() != null
        assert wrapper.getProperties() != null

        wrapper.getMailTarget().getUsername() == 'eliotalderson2'
        wrapper.getMailTarget().getTargetEmail() == 'mikolaj.kopernik@gmail.com'
    }

}
