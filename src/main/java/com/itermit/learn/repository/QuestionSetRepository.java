package com.itermit.learn.repository;

import com.itermit.learn.model.entity.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSet, Long> {
}
