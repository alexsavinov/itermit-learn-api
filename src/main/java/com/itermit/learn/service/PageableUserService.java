package com.itermit.learn.service;

import com.itermit.learn.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableUserService {

    Page<User> findAll(Pageable pageable, String search);
}
