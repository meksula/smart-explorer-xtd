package pl.smartexplorer.cerber.security.user;

import pl.smartexplorer.cerber.model.user.User;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

public interface UserRequestGateway {
    User allowReturnUserEntity(String username, String password);
}
