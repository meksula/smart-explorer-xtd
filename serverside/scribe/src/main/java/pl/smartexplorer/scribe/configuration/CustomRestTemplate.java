package pl.smartexplorer.scribe.configuration;

import pl.smartexplorer.scribe.model.dto.User;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

public interface CustomRestTemplate {
    Optional<User> requestForUser(String username);
}
