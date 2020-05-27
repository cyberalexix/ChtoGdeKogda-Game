package com.game.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {
    @GetMapping("/access_denied")
    public String accessDenied() {
        return "accessdenied";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String notFound() {
        return "notfound";
    }
}
