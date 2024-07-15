package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Source;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableSourceRepository
        extends PagingAndSortingRepository<Source, Long>, JpaSpecificationExecutor<Source> {
}
