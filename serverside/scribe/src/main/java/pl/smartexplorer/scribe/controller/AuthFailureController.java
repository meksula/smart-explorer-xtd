package pl.smartexplorer.scribe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthFailureController {

    @GetMapping("/login_error")
    public String loginError() {
        return "/login_fail";
    }

}

