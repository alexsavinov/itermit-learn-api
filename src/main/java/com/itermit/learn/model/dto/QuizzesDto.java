package com.itermit.learn.model.dto;

import com.itermit.learn.controller.QuizController;
import com.itermit.learn.model.entity.Quiz;
import com.itermit.learn.service.mapper.QuizMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class QuizzesDto extends RepresentationModelAssemblerSupport<Quiz, QuizDto> {

    private final QuizMapper quizMapper;

    public QuizzesDto(QuizMapper quizMapper) {
        super(QuizController.class, QuizDto.class);
        this.quizMapper = quizMapper;
    }

    @Override
    public QuizDto toModel(Quiz entity) {
        QuizDto quizDto = quizMapper.toBasicDto(entity);
        quizDto.add(linkTo(methodOn(QuizController.class).getQuizById(quizDto.getId())).withSelfRel());
        return quizDto;
    }
}
