package com.game.dto;

import com.game.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private String questionEn;
    private String hintEn;
    private String answerEn;
    private String questionUk;
    private String hintUk;
    private String answerUk;
    private int difficult;

    public QuestionDTO(Question question) {
        this.questionEn = question.getQuestionEn();
        this.questionUk = question.getQuestionUk();
        this.hintEn = question.getHintEn();
        this.hintUk = question.getHintUk();
        this.answerEn = question.getAnswerEn();
        this.answerUk = question.getAnswerUk();
        this.difficult = question.getDifficult();
    }
}
