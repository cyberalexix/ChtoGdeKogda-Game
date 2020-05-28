package com.game.controller;

import com.game.config.GameConfig;
import com.game.dto.QuestionDTO;
import com.game.dto.UserDTO;
import com.game.entity.Question;
import com.game.entity.User;
import com.game.repository.QuestionRepository;
import com.game.repository.UserRepository;
import com.game.service.QuestionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private GameConfig gameConfig;

    @GetMapping("/admin/user_list")
    public String adminUserList(
            Model model,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        Page<User> userPage = userRepository.findAll(PageRequest.of(currentPage-1, sizePage));
        model.addAttribute("userlist", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin_user_list";
    }

    @GetMapping("/admin/question_list")
    public String adminQuestionList(
            Model model,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        Page<Question> userPage = questionRepository.findAll(PageRequest.of(currentPage-1, sizePage));
        model.addAttribute("questionlist", userPage);
        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin_question_list";
    }

    @GetMapping("/admin/add_question")
    public String addQuestionPage(@RequestParam(value = "added", required = false) String added,
            Model model) {
        QuestionDTO questionDTO = new QuestionDTO();
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("added", added != null);
        return "admin_add_question";
    }

    @PostMapping("/admin/add_question")
    public String saveUser(@ModelAttribute("questionDTO") @Validated QuestionDTO questionDTO, BindingResult bindingResult, final RedirectAttributes redirectAttributes) throws Exception {
        if(bindingResult.hasErrors()) {
            return "admin_add_question";
        }
        questionService.saveQuestion(questionDTO);
        return "redirect:/admin/add_question?added";
    }
}
