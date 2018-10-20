package pl.smartexplorer.scribe.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.smartexplorer.scribe.model.dto.User;

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

}
