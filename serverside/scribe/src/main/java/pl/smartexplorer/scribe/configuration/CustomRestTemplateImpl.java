package pl.smartexplorer.scribe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.scribe.model.User;
import pl.smartexplorer.scribe.model.dto.UserRequest;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Slf4j
@Component
public class CustomRestTemplateImpl implements CustomRestTemplate {
    private RestTemplate restTemplate;

    public CustomRestTemplateImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Deprecated
    @Override
    public Optional<User> requestForUser(String username) {
        ResponseEntity<User> responseEntity;
        User user = null;
        
        try {
            responseEntity = restTemplate.postForEntity("http://localhost:8040", username, User.class);
            user = responseEntity.getBody();
        } catch (ResourceAccessException exception) {
            log.error("Cannot connect with :8040 [Scribe]");
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> requestForUser(String username, String password) {
        ResponseEntity<User> responseEntity;
        User user = null;

        try {
            responseEntity = restTemplate.postForEntity("http://localhost:8040/api/v2/user", buildUserRequest(username, password), User.class);
            user = responseEntity.getBody();
            log.info("Attempt to get user entity was successful.");
        } catch (RuntimeException ex) {
            log.error("Cannot connect with :8040 [Scribe]");
        }

        return Optional.ofNullable(user);
    }

    private UserRequest buildUserRequest(String username, String password) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        userRequest.setPassword(password);
        return userRequest;
    }

}
