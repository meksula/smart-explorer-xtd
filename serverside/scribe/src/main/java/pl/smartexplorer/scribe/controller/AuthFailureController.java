package pl.smartexplorer.scribe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * Karol Meksuła
 * 20-10-2018
 * */

@Controller
public class AuthFailureController {

    @GetMapping("/login_error")
    public String loginError() {
        return "/login_error";
    }

}

