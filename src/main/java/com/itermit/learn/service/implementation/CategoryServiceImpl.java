package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.model.dto.request.CreateCategoryRequest;
import com.itermit.learn.model.dto.request.UpdateCategoryRequest;
import com.itermit.learn.model.entity.Category;
import com.itermit.learn.repository.CategoryRepository;
import com.itermit.learn.repository.PageableCategoryRepository;
import com.itermit.learn.repository.specification.CategorySpecs;
import com.itermit.learn.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final PageableCategoryRepository pageableCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Category findById(Long id) {
        log.debug("Looking for an category with id {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested category not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an category with id {}", id);
        return category;
    }

    @Override
    public Page<Category> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving categories. Page request: {}", pageable);

        Page<Category> categories = pageableCategoryRepository.findAll(CategorySpecs.filter(params), pageable);

        log.info("Retrieved {} categories of {} total", categories.getSize(), categories.getTotalElements());
        return categories;
    }

    @Transactional
    @Override
    public Category create(CreateCategoryRequest createRequest) {
        log.debug("Creating a new category");

        Category newCategory = modelMapper.map(createRequest, Category.class);
        Category createdCategory = categoryRepository.save(newCategory);

        log.info("Created a new category with id {}", createdCategory.getId());
        return createdCategory;
    }

    @Transactional
    @Override
    public Category update(UpdateCategoryRequest updateRequest) {
        log.debug("Updating category");

        Category foundCategory = findById(updateRequest.getId());
        foundCategory.setName(updateRequest.getName());

        Category updatedCategory = categoryRepository.save(foundCategory);

        log.info("Updated an category with id {}", updatedCategory.getId());
        return updatedCategory;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting category with id {}", id);

        Category foundCategory = findById(id);

        categoryRepository.delete(foundCategory);

        log.info("Category with id {} is deleted", foundCategory.getId());
    }
}
