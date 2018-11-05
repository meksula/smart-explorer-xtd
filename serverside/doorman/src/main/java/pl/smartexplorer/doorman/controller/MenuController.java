package pl.smartexplorer.doorman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author
 * Karol Meksuła
 * 19-10-2018
 * */

@Controller
@RequestMapping("/")
public class MenuController {

    @GetMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public String join() {
        return "registration_choose";
    }

    @GetMapping("/about")
    @ResponseStatus(HttpStatus.OK)
    public String about() {
        return "about";
    }

}
