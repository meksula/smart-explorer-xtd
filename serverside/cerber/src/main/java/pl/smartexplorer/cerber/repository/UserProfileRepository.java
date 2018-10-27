package pl.smartexplorer.cerber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.smartexplorer.cerber.model.user.UserProfile;

import java.util.Optional;

/**
 * @author
 * Karol Meksuła
 * 26-10-2018
 * */

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String username);
}
