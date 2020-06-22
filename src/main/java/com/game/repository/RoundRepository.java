package com.game.repository;

import com.game.entity.Round;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAll();

    Optional<Round> findById(Long id);

    Page<Round> findAllByUserId(Long id, Pageable pageable);

    @Query("SELECT r FROM Round r WHERE r.isFinished = 0 AND r.user.id = :id")
    Optional<Round> findByUserIdAndIsFinished(Long id);

    @Query("SELECT r FROM Round r WHERE r.isFinished = 0 AND r.givenAnswer IS NOT NULL")
    List<Round> findByIsFinishedAndGivenAnswer(Pageable pageable);

    Round save(Round round);

    @Modifying
    @Query("UPDATE Round r SET r.givenAnswer = :answer WHERE r.id = :id")
    int updateRound(@Param("id") Long id, @Param("answer") String answer);

    @Modifying
    @Query("UPDATE Round r SET r.isHintGiven = 1 WHERE r.id = :id")
    int updateRound(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Round r SET r.isFinished = :isFinished, r.score = :score, r.isWon = :isWon WHERE r.id = :id")
    int updateRound(@Param("id") Long id, @Param("score") int score, @Param("isFinished") boolean isFinished, @Param("isWon") boolean isWon);
}
