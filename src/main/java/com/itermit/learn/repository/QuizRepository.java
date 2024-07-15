package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Quiz;
import com.itermit.learn.model.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByQuizAnswersContaining(QuizAnswer quizAnswer);

    @Query(
            value = "select * from quiz where " +
                    "(:categoryId = 0 or :categoryId != 0 and quiz.category_id=:categoryId) and" +
                    "(:sourceId = 0 or :sourceId != 0 and quiz.source_id=:sourceId) " +
                    "order by RANDOM() limit :limit",
            nativeQuery = true
    )
    List<Quiz> findRandom(Long categoryId, Long sourceId, Integer limit);
}
