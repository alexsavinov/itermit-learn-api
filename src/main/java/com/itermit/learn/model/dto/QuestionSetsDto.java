package com.itermit.learn.model.dto;

import com.itermit.learn.controller.QuestionSetController;
import com.itermit.learn.model.entity.QuestionSet;
import com.itermit.learn.service.mapper.QuestionSetMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class QuestionSetsDto extends RepresentationModelAssemblerSupport<QuestionSet, QuestionSetDto> {

    private final QuestionSetMapper questionSetMapper;

    public QuestionSetsDto(QuestionSetMapper questionSetMapper) {
        super(QuestionSetController.class, QuestionSetDto.class);
        this.questionSetMapper = questionSetMapper;
    }

    @Override
    public @NotNull QuestionSetDto toModel(@NotNull QuestionSet entity) {
        QuestionSetDto questionSetDto = questionSetMapper.toBasicDto(entity);
        questionSetDto
                .add(linkTo(methodOn(QuestionSetController.class).getQuestionSetById(questionSetDto.getId()))
                .withSelfRel());
        return questionSetDto;
    }
}
