package com.game.controller;

import com.game.config.GameConfig;
import com.game.dto.QuestionDTO;
import com.game.dto.QuestionDTOValidator;
import com.game.entity.Question;
import com.game.entity.Round;
import com.game.entity.User;
import com.game.service.QuestionService;
import com.game.service.RoundService;
import com.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoundService roundService;

    @Autowired
    private GameConfig gameConfig;

    @Autowired
    QuestionDTOValidator questionDTOValidator;

    @GetMapping("/admin/user_list")
    public String adminUserList(
            Model model,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        Page<User> userPage = userService.findAll(PageRequest.of(currentPage-1, sizePage));
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
        Page<Question> userPage = questionService.findAll(PageRequest.of(currentPage-1, sizePage));
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

    @GetMapping("/admin/question_list/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<Question> questionEntity = questionService.findById(id);
        if(!questionEntity.isPresent()) {
            return "redirect:/admin/question_list";
        }
        QuestionDTO questionDTO = new QuestionDTO(questionEntity.get());
        model.addAttribute("questionDTO", questionDTO);
        return "edit_question";
    }

    @PostMapping("/admin/question_list/{id}")
    public String postEditUser(@PathVariable Long id,
                               @ModelAttribute("questionDTO") @Validated QuestionDTO questionDTO,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "edit_question";
        }
        Optional<Question> question = questionService.findById(id);
        if(!question.isPresent()) {
            return "redirect:/admin/question_list";
        }
        System.out.println(question.get().getId());
        Question questionEntity = question.get();
        questionEntity.setQuestionEn(questionDTO.getQuestionEn());
        questionEntity.setQuestionUk(questionDTO.getQuestionUk());
        questionEntity.setHintEn(questionDTO.getHintEn());
        questionEntity.setHintUk(questionDTO.getHintUk());
        questionEntity.setAnswerEn(questionDTO.getAnswerEn());
        questionEntity.setAnswerUk(questionDTO.getAnswerUk());
        questionEntity.setDifficult(questionDTO.getDifficult());
        System.out.println(questionEntity);
        questionService.updateQuestion(questionEntity);
        return "redirect:/admin/question_list";
    }

    @GetMapping("/admin/add_new_question")
    public String addQuestionPage(@RequestParam(value = "added", required = false) String added,
            Model model) {
        QuestionDTO questionDTO = new QuestionDTO();
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("added", added != null);
        return "admin_add_question";
    }

    @PostMapping("/admin/add_new_question")
    public String saveUser(@ModelAttribute("questionDTO") @Validated QuestionDTO questionDTO, BindingResult bindingResult, final RedirectAttributes redirectAttributes) throws Exception {
        if(bindingResult.hasErrors()) {
            return "admin_add_question";
        }
        questionService.saveQuestion(questionDTO);
        return "redirect:/admin/add_new_question?added";
    }

    @GetMapping("/admin")
    public String getTableOfRounds(Model model,
                                   @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        model.addAttribute("roundslist", roundService.findByFinishedStatus(PageRequest.of(currentPage-1, sizePage)));
        model.addAttribute("lang", LocaleContextHolder.getLocaleContext().getLocale().getLanguage());
        return "admin_table";
    }

    @GetMapping("/admin/{id}/right")
    public String approveRound(Model model,
                               @PathVariable Long id) {
        Round round = roundService.findById(id).get();
        int multiplier = round.getIsHintGiven() ? 1 : 2;
        int score = round.getQuestion().getDifficult() * multiplier;
        roundService.finalUpdate(id, score, true, true);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/wrong")
    public String disapproveRound(Model model,
                               @PathVariable Long id) {
        Round round = roundService.findById(id).get();
        roundService.finalUpdate(id, 0, true, false);
        return "redirect:/admin";
    }

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }

        if (target.getClass() == QuestionDTO.class) {
            dataBinder.setValidator(questionDTOValidator);
        }
    }
}
