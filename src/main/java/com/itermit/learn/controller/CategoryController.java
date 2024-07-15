package com.itermit.learn.controller;

import com.itermit.learn.exception.ResourceReferencedFromTables;
import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.CategoriesDto;
import com.itermit.learn.model.dto.request.CreateCategoryRequest;
import com.itermit.learn.model.dto.request.UpdateCategoryRequest;
import com.itermit.learn.model.entity.Category;
import com.itermit.learn.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoriesDto categoriesDto;
    private final PagedResourcesAssembler<Category> pagedResourcesAssembler;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        Category foundCategory = categoryService.findById(id);
        CategoryDto categoryDto = modelMapper.map(foundCategory, CategoryDto.class);

        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategoryById(categoryDto.getId())).withSelfRel());
        return categoryDto;
    }

    @GetMapping
    public PagedModel<CategoryDto> getCategories(
            Pageable pageable,
            @RequestParam(required = false) Map<String, String> params) {
        Page<Category> foundCategories = categoryService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundCategories, categoriesDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody CreateCategoryRequest createRequest) {
        Category createdCategory = categoryService.create(createRequest);
        CategoryDto categoryDto = modelMapper.map(createdCategory, CategoryDto.class);

        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategoryById(categoryDto.getId())).withSelfRel());
        return categoryDto;
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody UpdateCategoryRequest updateRequest) {
        Category updatedCategory = categoryService.update(updateRequest);
        CategoryDto categoryDto = modelMapper.map(updatedCategory, CategoryDto.class);

        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategoryById(categoryDto.getId())).withSelfRel());
        return categoryDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceReferencedFromTables("Category exists in another tables. %s"
                    .formatted(e.getCause().getCause().getMessage().split(System.lineSeparator())[1]));
        }
    }
}
