package pl.smartexplorer.cerber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.smartexplorer.cerber.model.user.User;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
