package com.itermit.learn.service;

import com.itermit.learn.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableUserService {

    Page<User> findAll(Pageable pageable, Map<String, String> params);
}
