package pl.smartexplorer.doorman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author
 * Karol Meksuła
 * 19-10-2018
 * */

@Controller
@RequestMapping("/scribe/registration")
public class RegistrationController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void registrationScribe(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8030");
    }

}
