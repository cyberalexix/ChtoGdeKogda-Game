package com.game.dto;

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
}
