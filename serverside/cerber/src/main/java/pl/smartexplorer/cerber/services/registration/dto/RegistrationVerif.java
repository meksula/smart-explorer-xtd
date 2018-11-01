package pl.smartexplorer.cerber.services.registration.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * Karol Meksuła
 * 01-11-2018
 * */

@Data
@Entity
@Table(name = "verification")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RegistrationVerif {

    @Id
    private String userId;
    private String uuid;

}
