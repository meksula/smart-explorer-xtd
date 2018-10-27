package pl.smartexplorer.cerber.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import pl.smartexplorer.cerber.dto.TokenEstablishData
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.cerber.repository.TokenRepository
import pl.smartexplorer.cerber.security.MainTokenManager
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@SpringBootTest
@AutoConfigureMockMvc
class TokenEstablishControllerTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Autowired
    protected MainTokenManager tokenManager

    TokenRepository tokenRepository
    def mapper = new ObjectMapper()
    def tokenEstablishData = null
    def json = ""


    def setup() {
        tokenRepository = new ExpirableTokenRepository(jdbcTemplate)

        tokenEstablishData = new TokenEstablishData()
        tokenEstablishData.setUserId(null)
        tokenEstablishData.setUsername("janusznosacz")
        tokenEstablishData.setIpAddress("192.33.22.11")
        json = mapper.writeValueAsString(tokenEstablishData)
    }

    def "establish NEW token with SUCCESS test"() {
        when:
        def response = mockMvc.perform(post("/api/v2/token/establish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        then:
        println(json)
        response.andExpect(status().isCreated())

        and:
        response.andExpect(jsonPath('$.decision').value('true'))
        response.andExpect(jsonPath('$.sev2token').isNotEmpty())
        response.andExpect(jsonPath('$.message').value('Authentication with success.'))
    }

    def "establish NEW token FAILED test"() {
        setup: 'generate token for user'
        tokenManager.generateTokenAndSave(tokenEstablishData)

        when: 'now trying to duplicate users token'
        def response = mockMvc.perform(post("/api/v2/token/establish")
                .contentType(MediaType
                .APPLICATION_JSON).content(json))

        then:
        response.andExpect(status().isConflict())

        and:
        response.andExpect(jsonPath('$.decision').value('false'))
        response.andExpect(jsonPath('$.sev2token').isEmpty())
        response.andExpect(jsonPath('$.message').value('User just exist in database so it is impossible to create new same account.'))
    }

    def "update EXIST token SUCCESS test"() {
        setup: 'generate token for user and save to database'
        tokenManager.generateTokenAndSave(tokenEstablishData)

        when: 'try to update just generated token'
        def response = mockMvc.perform(post("/api/v2/token/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        then:
        response.andExpect(status().isOk())

        and:
        response.andExpect(jsonPath('$.decision').value('true'))
        response.andExpect(jsonPath('$.sev2token').isNotEmpty())
        response.andExpect(jsonPath('$.message').value('Authentication with success.'))
    }

    def "update EXIST token FAILED test"() {
        when: 'try to update token that not exist yet'
        def response = mockMvc.perform(post("/api/v2/token/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        then:
        response.andExpect(status().isConflict())

        and:
        response.andExpect(jsonPath('$.decision').value('false'))
        response.andExpect(jsonPath('$.sev2token').isEmpty())
        response.andExpect(jsonPath('$.message').value('User not exist in database.'))
    }

    /**
     * Drop table after each test because we don't know userId or token object or any data at all
     * */
    def cleanup() {
        tokenRepository.dropTable()
    }

}
