package pl.smartexplorer.cerber.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.cerber.dto.UserRequest;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.security.user.UserRequestGateway;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@RestController
@RequestMapping("/api/v2/user")
public class UserController {
    private UserRequestGateway userRequestGateway;

    public UserController(UserRequestGateway userRequestGateway) {
        this.userRequestGateway = userRequestGateway;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User retrieveUser(@RequestBody UserRequest userRequest) {
        return userRequestGateway.allowReturnUserEntity(userRequest.getUsername(), userRequest.getPassword());
    }

}
