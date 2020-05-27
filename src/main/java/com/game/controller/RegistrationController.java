package com.game.controller;

import com.game.dto.UserDTO;
import com.game.dto.UserDTOValidator;
import com.game.exception.UserAlreadyExistException;
import com.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public String saveUser(@ModelAttribute("userDTO") @Validated UserDTO userDTO, BindingResult bindingResult, final RedirectAttributes redirectAttributes) throws Exception {
        System.out.print(userDTO);
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(userDTO);
        return "redirect:/registerSuccessful";
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
    }

    @RequestMapping("/registered")
    public String registered() {
        return "registersuccess";
    }
}
