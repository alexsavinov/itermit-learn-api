package com.itermit.learn.controller;

import com.itermit.learn.model.dto.QuizDto;
import com.itermit.learn.model.dto.QuizzesDto;
import com.itermit.learn.model.dto.request.CreateQuizRequest;
import com.itermit.learn.model.dto.request.UpdateQuizRequest;
import com.itermit.learn.model.entity.Quiz;
import com.itermit.learn.service.QuizService;
import com.itermit.learn.service.mapper.QuizMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final QuizMapper quizMapper;
    private final QuizzesDto quizzesDto;
    private final PagedResourcesAssembler<Quiz> pagedResourcesAssembler;

    @GetMapping("/{id}")
    public QuizDto getQuizById(@PathVariable Long id) {
        Quiz foundQuiz = quizService.findById(id);

        QuizDto quizDto = quizMapper.toDto(foundQuiz);

        quizDto.add(linkTo(methodOn(QuizController.class).getQuizById(quizDto.getId())).withSelfRel());
        return quizDto;
    }

    @GetMapping("/search")
    public QuizDto getByQuizAnswerId(@RequestParam(name = "quiz-answer-id") Long quizAnswerId) {
        Quiz foundQuiz = quizService.findByQuizAnswerId(quizAnswerId);

        QuizDto quizDto = quizMapper.toDto(foundQuiz);

        quizDto.add(linkTo(methodOn(QuizController.class).getQuizById(quizDto.getId())).withSelfRel());
        return quizDto;
    }

    @GetMapping
    public PagedModel<QuizDto> getQuizzes(
            Pageable pageable,
            @RequestParam(required = false) Map<String, String> params) {
        Page<Quiz> foundQuizzes = quizService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundQuizzes, quizzesDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizDto addQuiz(@RequestBody CreateQuizRequest createRequest) {
        Quiz createdQuiz = quizService.create(createRequest);

        QuizDto quizDto = quizMapper.toDto(createdQuiz);

        quizDto.add(linkTo(methodOn(QuizController.class).addQuiz(createRequest)).withSelfRel());
        return quizDto;
    }

    @PatchMapping
    public QuizDto updateQuiz(@RequestBody UpdateQuizRequest updateRequest) {
        Quiz updatedQuiz = quizService.update(updateRequest);

        QuizDto quizDto = quizMapper.toDto(updatedQuiz);

        quizDto.add(linkTo(methodOn(QuizController.class).updateQuiz(updateRequest)).withSelfRel());
        return quizDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuizById(@PathVariable Long id) {
        quizService.delete(id);
    }

    @PostMapping("/saveImage")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map saveImage(MultipartFile file) {
        String fileName = quizService.saveImage(file);

        return Collections.singletonMap("location", fileName);
    }

}
