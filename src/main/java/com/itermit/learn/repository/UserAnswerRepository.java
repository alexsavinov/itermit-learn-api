package com.itermit.learn.repository;

import com.itermit.learn.model.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    boolean existsBySessionIdAndQuestionId(Long sessionId, Long questionId);

    Optional<UserAnswer> findBySessionIdAndQuestionId(Long sessionId, Long questionId);
}