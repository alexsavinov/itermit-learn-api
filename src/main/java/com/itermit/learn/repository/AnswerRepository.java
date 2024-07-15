package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}