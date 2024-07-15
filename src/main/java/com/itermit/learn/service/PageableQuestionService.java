package com.itermit.learn.service;

import com.itermit.learn.model.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableQuestionService {

    Page<Question> findAll(Pageable pageable, Map<String, String> params);
}
