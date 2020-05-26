package com.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @GetMapping
    public String showPage() {
        return "registration";
    }

    @PostMapping
    public String saveUser() {
        return "reg";
    }
}
