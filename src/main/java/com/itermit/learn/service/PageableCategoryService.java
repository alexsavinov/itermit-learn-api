package com.itermit.learn.service;

import com.itermit.learn.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PageableCategoryService {

    Page<Category> findAll(Pageable pageable, Map<String, String> params);
}
