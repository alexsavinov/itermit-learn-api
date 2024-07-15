package com.itermit.learn.repository;

import com.itermit.learn.model.entity.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableSessionRepository
        extends PagingAndSortingRepository<Session, Long>, JpaSpecificationExecutor<Session> {

    Page<Session> findAllByUserId(Long userId, Pageable page);
}
