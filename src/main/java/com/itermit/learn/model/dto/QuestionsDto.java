package com.itermit.learn.model.dto;

import com.itermit.learn.controller.QuestionController;
import com.itermit.learn.model.entity.Question;
import com.itermit.learn.service.mapper.QuestionMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class QuestionsDto extends RepresentationModelAssemblerSupport<Question, QuestionDto> {

    private final QuestionMapper questionMapper;

    public QuestionsDto(QuestionMapper questionMapper) {
        super(QuestionController.class, QuestionDto.class);
        this.questionMapper = questionMapper;
    }

    @Override
    public @NotNull QuestionDto toModel(@NotNull Question entity) {
        QuestionDto quizDto = questionMapper.toBasicDto(entity);
        quizDto.add(linkTo(methodOn(QuestionController.class).getQuestionById(quizDto.getId())).withSelfRel());
        return quizDto;
    }
}
