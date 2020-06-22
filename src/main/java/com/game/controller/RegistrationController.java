package com.game.controller;

import com.game.dto.UserDTO;
import com.game.dto.UserDTOValidator;
import com.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    UserService userService;
    UserDTOValidator userDTOValidator;

    @Autowired
    public RegistrationController(UserService userService, UserDTOValidator userDTOValidator) {
        this.userService = userService;
        this.userDTOValidator = userDTOValidator;
    }

    @GetMapping
    public String showPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "registration";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("userDTO") @Validated UserDTO userDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(userDTO);
        return "redirect:/login?registered";
    }

    @RequestMapping("/registered")
    public String registered() {
        return "registersuccess";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == UserDTO.class) {
            dataBinder.setValidator(userDTOValidator);
        }
    }
}
