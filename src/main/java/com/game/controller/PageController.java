package com.game.controller;

import com.game.dto.QuestionDTO;
import com.game.dto.QuestionDTOValidator;
import com.game.dto.UserDTO;
import com.game.dto.UserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    UserDTOValidator userDTOValidator;

    @Autowired
    QuestionDTOValidator questionDTOValidator;

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "registered", required = false) String registered,
                            Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("registered", registered != null);
        return "login";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == UserDTO.class) {
            dataBinder.setValidator(userDTOValidator);
        }
        if (target.getClass() == QuestionDTO.class) {
            dataBinder.setValidator(questionDTOValidator);
        }
    }
}
