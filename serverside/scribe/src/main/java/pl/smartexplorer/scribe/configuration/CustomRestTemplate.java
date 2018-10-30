package pl.smartexplorer.scribe.configuration;

import pl.smartexplorer.scribe.model.User;
import pl.smartexplorer.scribe.model.dto.CerberAuthDecission;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

public interface CustomRestTemplate {
    Optional<User> requestForUser(String username);

    Optional<User> requestForUser(String username, String password);

    Optional<CerberAuthDecission> requestForUserAndSev2Token(String username, String password);
}
