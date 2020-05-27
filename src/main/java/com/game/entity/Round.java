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
    @JoinColumn(name = "student_id")
    Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "hint_given", nullable = false)
    private boolean isHintGiven;

    @Column(name = "time_start", nullable = false)
    private Timestamp timeStarted;

    @Column(name = "is_finished", nullable = false)
    private boolean isFinished;

    @Column(name = "is_won", nullable = false)
    private boolean isWon;

    @Column(name = "score", nullable = false)
    private int score;
}
