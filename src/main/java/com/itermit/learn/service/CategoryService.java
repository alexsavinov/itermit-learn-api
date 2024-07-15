package com.itermit.learn.service;

import com.itermit.learn.model.dto.request.CreateCategoryRequest;
import com.itermit.learn.model.dto.request.UpdateCategoryRequest;
import com.itermit.learn.model.entity.Category;


public interface CategoryService extends PageableCategoryService {

    Category findById(Long id);

    Category create(CreateCategoryRequest createRequest);

    Category update(UpdateCategoryRequest updateRequest);

    void delete(Long id);
}
