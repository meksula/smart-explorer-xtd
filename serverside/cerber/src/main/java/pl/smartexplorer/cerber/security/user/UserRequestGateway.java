package pl.smartexplorer.cerber.security.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.model.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

public interface UserRequestGateway {
    User allowReturnUserEntity(String username, String password);

    CerberAuthDecission allowReturnCerberDesision(String username, String password, HttpServletRequest request) throws JsonProcessingException;
}
