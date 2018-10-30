package pl.smartexplorer.cerber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.smartexplorer.cerber.dto.CerberAuthDecission;
import pl.smartexplorer.cerber.dto.UserRequest;
import pl.smartexplorer.cerber.model.user.User;
import pl.smartexplorer.cerber.security.user.UserRequestGateway;

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

}
