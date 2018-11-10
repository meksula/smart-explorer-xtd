package pl.smartexplorer.cerber.controller

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.util.NestedServletException
import pl.smartexplorer.cerber.dto.CerberAuthDecisionRegistration
import pl.smartexplorer.cerber.dto.TokenEstablishData
import pl.smartexplorer.cerber.dto.UserRequest
import pl.smartexplorer.cerber.model.user.AuthenticationType
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.ExpirableTokenRepository
import pl.smartexplorer.cerber.repository.RegistrationVerifRepository
import pl.smartexplorer.cerber.repository.TokenRepository
import pl.smartexplorer.cerber.repository.UserRepository
import pl.smartexplorer.cerber.security.TokenManager
import pl.smartexplorer.cerber.services.registration.UserRegistrationModel
import pl.smartexplorer.cerber.services.registration.UserRegistrator
import spock.lang.Specification

import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    protected PasswordEncoder passwordEncoder

    @Autowired
    protected UserRepository userRepository

    @Autowired
    protected TokenManager tokenManager

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Autowired
    protected UserRegistrator userRegistrator

    @Autowired
    protected RegistrationVerifRepository verifRepository

    protected TokenRepository tokenRepository

    def userRequest = null
    protected User user
    def username = "testuser"
    def password = "password"
    String requestUserJson = null

    def setup() {
        tokenRepository = new ExpirableTokenRepository(jdbcTemplate)

        userRequest = new UserRequest()
        userRequest.setUsername("testuser")
        userRequest.setPassword("password")

        user = new User()
        user.setUserId("januSnHKdM")
        user.setAuthenticationType(AuthenticationType.SCRIBE.name())
        user.setSocialServiceId("203kd32d023")
        user.setSocialUsername("426264179650124")
        user.setUsername(username)
        user.setEncryptedPassword(passwordEncoder.encode(password))
        user.setEmail("testmail@gmail.com")
        user.setJoinDate(LocalDateTime.now().toString())
        user.setAccountNonLocked(true)
        user.setAccountNonExpired(true)
        user.setCredentialsNonExpired(true)
        user.setEnabled(true)

        requestUserJson = new ObjectMapper().writeValueAsString(userRequest)
    }

    def "retrieveUser() should return user"() {
        setup:
        userRepository.save(user)

        when:
        def response = mockMvc.perform(post("/api/v2/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUserJson))

        then:
        response.andExpect(status().isOk())
        response.andExpect(jsonPath('$.email').value('testmail@gmail.com'))
        response.andExpect(jsonPath('$.socialUsername').value('426264179650124'))
    }

    def "retrieveUser() should NOT return user"() {
        when:
        mockMvc.perform(post("/api/v2/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUserJson))

        then:
        thrown(NestedServletException)
    }

    def "authenticateUserAndReturnDecision should return SUCCESSFULY prepared entity" () {
        setup:
        userRepository.save(user)
        def estData = new TokenEstablishData()
        estData.setUserId(user.getUserId())
        estData.setUsername("testuser")
        estData.setIpAddress("192.29.22.34")
        log.info(estData.toString())
        tokenManager.generateTokenAndSave(estData)

        when:
        def response = mockMvc.perform(post("/api/v2/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUserJson))

        then:
        response.andExpect(status().isOk())
        response.andExpect(jsonPath('$.decision').value('true'))
        response.andExpect(jsonPath('$.sev2token').isNotEmpty())
        response.andExpect(jsonPath('$.message').isNotEmpty())
        response.andExpect(jsonPath('$.user').isNotEmpty())

        cleanup:
        tokenRepository.dropTable()
    }

    def "authenticateUserAndReturnDecision should return USER but token generate is FAILED test"() {
        setup:
        userRepository.save(user)
        def estData = new TokenEstablishData()
        estData.setUserId(user.getUserId())
        estData.setUsername("testuser")
        estData.setIpAddress("192.29.22.34")

        when:
        def response = mockMvc.perform(post("/api/v2/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestUserJson)).andDo(print())

        then:
        response.andExpect(status().isOk())
        response.andExpect(jsonPath('$.decision').value('false'))
        response.andExpect(jsonPath('$.sev2token').isEmpty())
        response.andExpect(jsonPath('$.message').value('User retrieved successfully but some error occured while sev2token update'))
        response.andExpect(jsonPath('$.user').isNotEmpty())
    }

    def "registerUser should register user with SUCCESS"() {
        given:
        def user = UserRegistrationModel.buildUser()
        def JSON = new ObjectMapper().writeValueAsString(user)

        when:
        ResultActions response = mockMvc.perform(post("/api/v2/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON))
                .andDo(print())

        then:
        response.andExpect(status().isCreated())
        response.andExpect(jsonPath('$.verificationUuid').isNotEmpty())
        response.andExpect(jsonPath('$.user').isNotEmpty())
        response.andExpect(jsonPath('$.decision').value('true'))
        response.andExpect(jsonPath('$.message').value("User was created successfully"))

        cleanup:
        tokenRepository.dropTable()
        userRepository.delete(userRepository.findByUsername(user.getUsername()).get())
    }

    def "verification should processed with SUCCESS - integration test"() {
        setup:
        def user = UserRegistrationModel.buildUser()
        CerberAuthDecisionRegistration cadr = userRegistrator.registerUser(user, "192.33.333.24")
        def resultBoolean = ""

        when:
        def response = mockMvc.perform(get("/api/v2/user/registration/verification/" + cadr.getUser().getUserId() + "/" + cadr.getVerificationUuid()))

        response.andDo(new ResultHandler() {
            @Override
            void handle(MvcResult result) throws Exception {
                resultBoolean = result.getResponse().getContentAsString()
            }
        })

        then:
        response.andExpect(status().isOk())
        assert resultBoolean == "true"

        cleanup:
        tokenRepository.dropTable()
        userRepository.delete(userRepository.findByUsername(user.getUsername()).get())
        verifRepository.deleteAll()
    }

    def "verification should processed with FAILURE - integration test"() {
        setup:
        def user = UserRegistrationModel.buildUser()
        CerberAuthDecisionRegistration cadr = userRegistrator.registerUser(user, "192.33.333.24")
        def resultBoolean = ""

        when:'here change correct UUID to invalid'
        cadr.setVerificationUuid(UUID.randomUUID().toString())
        def response = mockMvc.perform(get("/api/v2/user/registration/verification/" + cadr.getUser().getUserId() + "/" + cadr.getVerificationUuid()))

        response.andDo(new ResultHandler() {
            @Override
            void handle(MvcResult result) throws Exception {
                resultBoolean = result.getResponse().getContentAsString()
            }
        })

        then:
        response.andExpect(status().isOk())
        assert resultBoolean == "false"

        cleanup:
        tokenRepository.dropTable()
        userRepository.delete(userRepository.findByUsername(user.getUsername()).get())
        verifRepository.deleteAll()
    }

    def cleanup() {
        userRepository.delete(user)
    }

}
