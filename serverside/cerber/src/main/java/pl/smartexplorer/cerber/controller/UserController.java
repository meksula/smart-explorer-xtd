package pl.smartexplorer.cerber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.UserRequest;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.security.user.UserRequestGateway;
import pl.smartexplorer.cerber.services.registration.UserRegistrator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

@AllArgsConstructor
@RestController
@RequestMapping("/api/v2/user")
public class UserController {
    private UserRequestGateway userRequestGateway;
    private UserRegistrator userRegistrator;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User retrieveUser(@RequestBody UserRequest userRequest) {
        return userRequestGateway.allowReturnUserEntity(userRequest.getUsername(), userRequest.getPassword());
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public CerberAuthDecission authenticateUserAndReturnDecision(@RequestBody UserRequest userRequest, HttpServletRequest request) throws JsonProcessingException {
        return userRequestGateway.allowReturnCerberDesision(userRequest.getUsername(), userRequest.getPassword(), request);
    }

    /**
     * This endpoint register new user. Notice that created user is disabled!
     * Registered user can confirm your account with other endpoint /registration/verification/{uuid}
     * */
    @PutMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user, HttpServletRequest request) {
        return userRegistrator.registerUser(user, request.getRemoteAddr());
    }

    @GetMapping("/registration/verification/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void registrationVerification(@PathVariable String uuid) {
        //TODO
    }

}
