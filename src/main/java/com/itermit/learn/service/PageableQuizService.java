package com.itermit.learn.service;

import com.itermit.learn.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableQuizService {

    Page<Quiz> findAll(Pageable pageable, Map<String, String> params);
}
