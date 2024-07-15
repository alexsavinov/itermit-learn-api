package com.itermit.learn.repository;

import com.itermit.learn.model.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PageableUserRepository
        extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
}
