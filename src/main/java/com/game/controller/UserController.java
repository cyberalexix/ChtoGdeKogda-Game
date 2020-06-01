package com.game.controller;

import com.game.config.GameConfig;
import com.game.entity.Question;
import com.game.entity.Round;
import com.game.entity.UserDetailsImpl;
import com.game.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {
    @Autowired
    RoundService roundService;

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
        int currentPage = page.orElse(1);
        int sizePage = Integer.parseInt(gameConfig.getValue("pagination.size"));
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
