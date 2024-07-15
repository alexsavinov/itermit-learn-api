package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableQuestionRepository
        extends PagingAndSortingRepository<Question, Long>, JpaSpecificationExecutor<Question> {
}
