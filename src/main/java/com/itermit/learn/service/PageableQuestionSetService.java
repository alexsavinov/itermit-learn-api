package com.itermit.learn.service;

import com.itermit.learn.model.entity.QuestionSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableQuestionSetService {

    Page<QuestionSet> findAll(Pageable pageable, Map<String, String> params);
}
