package com.game.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rounds")
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "hint_given", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isHintGiven;

    @Column(name = "time_start", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timeStarted;

    @Column(name = "is_finished", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isFinished;

    @Column(name = "given_answer")
    private String givenAnswer;

    @Column(name = "is_won", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isWon;

    @Column(name = "score", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private int score;
}
