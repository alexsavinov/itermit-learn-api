package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableQuizRepository
        extends PagingAndSortingRepository<Quiz, Long>, JpaSpecificationExecutor<Quiz> {
}
