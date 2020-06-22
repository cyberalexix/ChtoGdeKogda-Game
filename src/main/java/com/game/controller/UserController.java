package com.game.controller;

import com.game.config.GameConfig;
import com.game.dto.RoundDTO;
import com.game.entity.Round;
import com.game.entity.UserDetailsImpl;
import com.game.service.QuestionService;
import com.game.service.RoundService;
import com.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {
    @Autowired
    RoundService roundService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    GameConfig gameConfig;

    @GetMapping("/user")
    public String userPage(Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/user/" + userDetails.getId();
    }

    @GetMapping("/user/{id}")
    public String userPageById(Model model,
                               @PathVariable Long id,
                               @RequestParam("page") Optional<Integer> page) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!id.equals(userDetails.getId()) && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))  {
            return "redirect:/access_denied";
        }
        if(!userService.findById(id).isPresent()) {
            return "redirect:/user";
        }
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        Page<Round> roundList = roundService.findAllByUserId(userDetails.getId(), PageRequest.of(currentPage-1, sizePage));
        model.addAttribute("roundslist", roundList);
        model.addAttribute("lang", LocaleContextHolder.getLocaleContext().getLocale().getLanguage());
        int totalPages = roundList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "user";
    }

    @GetMapping("/user/new_round")
    public String newRound() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Round> queriedRound = roundService.findLastUserRound(userDetails.getId());
        if(queriedRound.isPresent()) {
            return "redirect:/user/round/" + queriedRound.get().getId();
        } else {
            Round round = new Round();
            round.setTimeStarted(new Timestamp(System.currentTimeMillis()));
            round.setUser(userService.findById(userDetails.getId()).get());
            round.setQuestion(questionService.getRandomQuestion());
            roundService.save(round);
            return "redirect:/user/new_round";
        }
    }

    @GetMapping("/user/round/{id}")
    public String getRoundPage(Model model,
                               @PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Round> roundColumn = roundService.findById(id);roundService.findById(id).isPresent();
        if(!roundColumn.isPresent()) {
            return "redirect:/user";
        }
        Round round = roundColumn.get();
        if(!round.getUser().getId().equals(userDetails.getId()) && !userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))  {
            return "redirect:/access_denied";
        }
        if(LocaleContextHolder.getLocaleContext().getLocale().getLanguage().equals("uk")) {
            model.addAttribute("question", round.getQuestion().getQuestionUk());
            if(round.getIsHintGiven()) {
                model.addAttribute("hint", round.getQuestion().getHintUk());
            }
        } else {
            model.addAttribute("question", round.getQuestion().getQuestionEn());
            if(round.getIsHintGiven()) {
                model.addAttribute("hint", round.getQuestion().getHintEn());
            }
        }
        RoundDTO roundDTO = new RoundDTO();
        model.addAttribute("roundDTO", roundDTO);
        model.addAttribute("id", round.getId());
        return "round";
    }

    @PostMapping("/user/round/{id}")
    public String getRoundPage(Model model,
                               @PathVariable Long id,
                               @ModelAttribute("roundDTO") RoundDTO roundDTO) {
        roundService.updateAnswer(id, roundDTO.getAnswer());
        return "redirect:/user";
    }

    @PostMapping("/user/round/{id}/get_hint")
    public String getHint(Model model,
                               @PathVariable Long id) {
        roundService.updateHint(id);
        return "redirect:/user/new_round";
    }
}
