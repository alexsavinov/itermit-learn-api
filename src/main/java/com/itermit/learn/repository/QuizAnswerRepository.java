package com.itermit.learn.repository;

import com.itermit.learn.model.entity.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}