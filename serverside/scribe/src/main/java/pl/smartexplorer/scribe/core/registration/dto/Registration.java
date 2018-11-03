package pl.smartexplorer.scribe.core.registration.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * @author
 * Karol Meksuła
 * 02-11-2018
 * */

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Registration {
    public static final String REGEX = "^[a-zA-Z0-9łóżźćńę\\s]{6,40}$";
    public static final String PASSWORD = "^[a-zA-Z0-9,./?'!@#$%^&*()_\\-+=]{7,30}$";
    public static final String PASSWORD_MSG = "Hasło musi skladać się minimum z 7 znaków (Bez znaków polskich)";
    public static final String NAMES_MSG = "Nazwa musi składać się minimum z 6 znaków, bez znaków polskich i znaków specjalnych";

    @Null
    private String userId;

    @NotNull
    @Length(min = 6, max = 8)
    private String authenticationType;

    @Pattern(regexp = REGEX, message = NAMES_MSG)
    private String socialServiceId;

    @Pattern(regexp = REGEX, message = NAMES_MSG)
    private String socialUsername;

    @NotNull
    @Pattern(regexp = REGEX, message = NAMES_MSG)
    private String username;

    @Pattern(regexp = PASSWORD, message = PASSWORD_MSG)
    private String password;

    @Pattern(regexp = PASSWORD, message = PASSWORD_MSG)
    private String passwordRepeated;

    @NotNull
    @Email(message = "Ten adres jest nie poprawny.")
    private String email;

    @Null
    private String joinDate;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Cannot parse";
        }
    }
}
