package com.game.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "question_en", nullable = false)
    private String questionEn;

    @Column(name = "hint_en", nullable = false)
    private String hintEn;

    @Column(name = "answer_en", nullable = false)
    private String answerEn;

    @Column(name = "question_uk", nullable = false)
    private String questionUk;

    @Column(name = "hint_uk", nullable = false)
    private String hintUk;

    @Column(name = "answer_uk", nullable = false)
    private String answerUk;

    @Column(name = "difficult", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private int difficult;

    @OneToMany(mappedBy = "question")
    Set<Round> rounds;
}
