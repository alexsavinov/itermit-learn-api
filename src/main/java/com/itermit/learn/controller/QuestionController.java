package com.itermit.learn.controller;

import com.itermit.learn.model.dto.QuestionDto;
import com.itermit.learn.model.dto.QuestionsDto;
import com.itermit.learn.model.dto.request.CreateQuestionRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionRequest;
import com.itermit.learn.model.entity.Question;
import com.itermit.learn.service.QuestionService;
import com.itermit.learn.service.mapper.QuestionMapper;
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
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final QuestionsDto questionsDto;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;

    @GetMapping("/{id}")
    public QuestionDto getQuestionById(@PathVariable Long id) {
        Question foundQuestion = questionService.findById(id);
        QuestionDto questionDto = questionMapper.toDto(foundQuestion);

        questionDto.add(linkTo(methodOn(QuestionController.class).getQuestionById(questionDto.getId())).withSelfRel());
        return questionDto;
    }

    @GetMapping
    public PagedModel<QuestionDto> getQuestions(
            Pageable pageable,
            @RequestParam(required = false) Map<String, String> params) {
        Page<Question> foundQuestions = questionService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundQuestions, questionsDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto addQuestion(@RequestBody CreateQuestionRequest createRequest) {
        Question createdQuestion = questionService.create(createRequest);
        QuestionDto questionDto = questionMapper.toDto(createdQuestion);

        questionDto.add(linkTo(methodOn(QuestionController.class).getQuestionById(questionDto.getId())).withSelfRel());
        return questionDto;
    }

    @PatchMapping
    public QuestionDto updateQuestion(@RequestBody UpdateQuestionRequest updateRequest) {
        Question updatedQuestion = questionService.update(updateRequest);
        QuestionDto questionDto = questionMapper.toDto(updatedQuestion);

        questionDto.add(linkTo(methodOn(QuestionController.class).getQuestionById(questionDto.getId())).withSelfRel());
        return questionDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionById(@PathVariable Long id) {
        questionService.delete(id);
    }

    @PostMapping("/saveImage")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map saveImage(MultipartFile file) {
        String fileName = questionService.saveImage(file);
        return Collections.singletonMap("location", fileName);
    }

}
