package com.game.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "rounds")
public class Round implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    Question question;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
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

    public boolean getIsHintGiven() {
        return isHintGiven;
    }

    public boolean getIsWon() {
        return isWon;
    }

    public boolean getIsFinished() {
        return isFinished;
    }
}
