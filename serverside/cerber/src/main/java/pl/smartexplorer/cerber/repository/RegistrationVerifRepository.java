package pl.smartexplorer.cerber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.smartexplorer.cerber.services.registration.dto.RegistrationVerif;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

public interface RegistrationVerifRepository extends JpaRepository<RegistrationVerif, String> {
}
