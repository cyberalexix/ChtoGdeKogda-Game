package com.game.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.FetchMode.SELECT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "questions")
public class Question implements Serializable {
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

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @Fetch(value = SELECT)
    List<Round> rounds = new ArrayList<>();
}
