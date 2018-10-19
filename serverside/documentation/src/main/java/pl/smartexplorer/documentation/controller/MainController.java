package pl.smartexplorer.documentation.controller;

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
@RequestMapping("/")
public class MainController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String documentationHome() {
        return "index";
    }

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public String mainPage() {
        return "index";
    }

    @GetMapping("/app")
    @ResponseStatus(HttpStatus.OK)
    public void goApplication(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("http://localhost:8010");
    }

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public String quickStart() {
        return "start";
    }

    @GetMapping("/reference")
    @ResponseStatus(HttpStatus.OK)
    public String reference() {
        return "reference";
    }

    @GetMapping("/examples")
    @ResponseStatus(HttpStatus.OK)
    public String examples() {
        return "examples";
    }

    @GetMapping("/workflows")
    @ResponseStatus(HttpStatus.OK)
    public String workflows() {
        return "workflows";
    }

    @GetMapping("/other")
    @ResponseStatus(HttpStatus.OK)
    public String other() {
        return "other";
    }

    @GetMapping("/author")
    @ResponseStatus(HttpStatus.OK)
    public String author() {
        return "author";
    }

}
