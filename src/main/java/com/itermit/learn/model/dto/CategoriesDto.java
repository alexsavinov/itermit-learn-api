package com.itermit.learn.model.dto;

import com.itermit.learn.controller.CategoryController;
import com.itermit.learn.model.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class CategoriesDto extends RepresentationModelAssemblerSupport<Category, CategoryDto> {


    private final ModelMapper modelMapper;

    public CategoriesDto(ModelMapper modelMapper) {
        super(CategoryController.class, CategoryDto.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto toModel(Category entity) {
        CategoryDto categoryDto = modelMapper.map(entity, CategoryDto.class);
        categoryDto.add(linkTo(methodOn(CategoryController.class).getCategoryById(categoryDto.getId())).withSelfRel());
        return categoryDto;
    }
}
