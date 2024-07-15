package com.itermit.learn.model.dto;

import com.itermit.learn.controller.SourceController;
import com.itermit.learn.model.entity.Source;
import com.itermit.learn.service.mapper.SourceMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class SourcesDto extends RepresentationModelAssemblerSupport<Source, SourceDto> {

    private final SourceMapper quizMapper;

    public SourcesDto(SourceMapper quizMapper) {
        super(SourceController.class, SourceDto.class);
        this.quizMapper = quizMapper;
    }

    @Override
    public SourceDto toModel(Source entity) {
        SourceDto quizDto = quizMapper.toDto(entity);
        quizDto.add(linkTo(methodOn(SourceController.class).getSourceById(quizDto.getId())).withSelfRel());
        return quizDto;
    }
}
