package com.game.dto;

import com.game.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class QuestionDTOValidator implements Validator {
    @Autowired
    private QuestionService questionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == QuestionDTO.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        QuestionDTO questionDTO = (QuestionDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionEn", "empty.question.en");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "questionUk", "empty.question.uk");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hintEn", "empty.hint.en");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hintUk", "empty.hint.uk");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answerEn", "empty.answer.en");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answerUk", "empty.answer.uk");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "difficult", "empty.difficult");
    }
}
