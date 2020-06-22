package com.game.repository;

import com.game.entity.Question;
import com.game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAll();

    Optional<Question> findById(Long id);

    Question save(Question question);

    @Query("UPDATE Question q SET q.questionEn = :questionEn, q.questionUk = :questionUk, q.hintEn = :hintEn, q.hintUk = :hintUk, q.answerEn = :answerEn, q.answerUk = :answerUk, q.difficult = :difficult WHERE q.id = :id")
    void updateQuestion(@Param("id") Long id, @Param("questionEn") String questionEn, @Param("questionUk") String questionUk, @Param("hintEn") String hintEn, @Param("hintUk") String hintUk, @Param("answerEn") String answerEn, @Param("answerUk") String answerUk, @Param("difficult") int difficult);

    void deleteById(Long id);

    @Query(value = "SELECT * FROM questions AS q ORDER BY rand() LIMIT 1", nativeQuery = true)
    Question getRandQuestion();
}
