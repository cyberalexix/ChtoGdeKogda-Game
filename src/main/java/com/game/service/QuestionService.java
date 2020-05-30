package com.game.service;

import com.game.dto.QuestionDTO;
import com.game.entity.Question;
import com.game.repository.QuestionRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question saveQuestion(QuestionDTO questionDTO) {
        return questionRepository.save(Question.builder()
                .questionEn(questionDTO.getQuestionEn())
                .questionUk(questionDTO.getQuestionUk())
                .hintEn(questionDTO.getHintEn())
                .hintUk(questionDTO.getHintUk())
                .answerEn(questionDTO.getAnswerEn())
                .answerUk(questionDTO.getAnswerUk())
                .difficult(questionDTO.getDifficult())
                .build());
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }
}
