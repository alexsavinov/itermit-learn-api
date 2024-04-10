package com.itermit.learn.service;

import com.itermit.learn.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableArticleService {

    Page<Article> findAll(Pageable pageable, String search);
}
