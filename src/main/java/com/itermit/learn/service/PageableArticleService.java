package com.itermit.learn.service;

import com.itermit.learn.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableArticleService {

    Page<Article> findAll(Pageable pageable, Map<String, String> params);
}
