package com.itermit.learn.repository;

import com.itermit.learn.model.entity.QuestionSet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableQuestionSetRepository
        extends PagingAndSortingRepository<QuestionSet, Long>, JpaSpecificationExecutor<QuestionSet> {
}
