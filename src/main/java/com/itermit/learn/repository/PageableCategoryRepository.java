package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableCategoryRepository
        extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
