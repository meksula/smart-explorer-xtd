package pl.smartexplorer.cerber.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import pl.smartexplorer.cerber.dto.UserRequest
import pl.smartexplorer.cerber.model.user.AuthenticationType
import pl.smartexplorer.cerber.model.user.User
import pl.smartexplorer.cerber.repository.UserRepository
import spock.lang.Specification

import java.time.LocalDateTime

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
class UserControllerTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    protected PasswordEncoder passwordEncoder

    @Autowired
    protected UserRepository userRepository

    def userRequest = null
    protected User user
    def username = "testuser"
    def password = "password"

    def setup() {
        userRequest = new UserRequest()
        userRequest.setUsername("testuser")
        userRequest.setPassword("password")

        user = new User()
        user.setUserId("93kd932d4")
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
    }

    def "controller should return user"() {
        when:
        def response = mockMvc.perform(post("/api/v2/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest)))

        then:
        response.andExpect(status().isOk())
        response.andExpect(jsonPath('$.email').value('testmail@gmail.com'))
        response.andExpect(jsonPath('$.socialUsername').value('426264179650124'))
    }

    def "controller should NOT return user"() {
        when:
        def response = mockMvc.perform(post("/api/v2/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest)))

        then:
        response.andExpect(status().isOk())
        response.andExpect(jsonPath('$.email').isEmpty())
        response.andExpect(jsonPath('$.socialUsername').isEmpty())
    }

    def cleanup() {
        userRepository.delete(user)
    }

}
