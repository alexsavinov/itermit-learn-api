package com.itermit.learn.controller;

import com.itermit.learn.exception.ResourceReferencedFromTables;
import com.itermit.learn.model.dto.QuestionSetDto;
import com.itermit.learn.model.dto.QuestionSetsDto;
import com.itermit.learn.model.dto.request.CreateQuestionSetRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionSetRequest;
import com.itermit.learn.model.entity.QuestionSet;
import com.itermit.learn.service.QuestionSetService;
import com.itermit.learn.service.mapper.QuestionSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping("/question-sets")
public class QuestionSetController {

    private final QuestionSetService questionSetService;
    private final QuestionSetsDto questionSetsDto;
    private final PagedResourcesAssembler<QuestionSet> pagedResourcesAssembler;
    private final QuestionSetMapper questionSetMapper;

    @GetMapping("/{id}")
    public QuestionSetDto getQuestionSetById(@PathVariable Long id) {
        QuestionSet foundQuestionSet = questionSetService.findById(id);
        QuestionSetDto questionSetDto = questionSetMapper.toDto(foundQuestionSet);

        questionSetDto.add(linkTo(methodOn(QuestionSetController.class).getQuestionSetById(questionSetDto.getId())).withSelfRel());
        return questionSetDto;
    }

    @GetMapping
    public PagedModel<QuestionSetDto> getQuestionSets(
            Pageable pageable,
            @RequestParam(required = false) Map<String, String> params) {
        Page<QuestionSet> foundQuestionSets = questionSetService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundQuestionSets, questionSetsDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionSetDto addQuestionSet(@RequestBody CreateQuestionSetRequest createRequest) {
        QuestionSet createdQuestionSet = questionSetService.create(createRequest);
        QuestionSetDto questionSetDto = questionSetMapper.toDto(createdQuestionSet);

        questionSetDto.add(linkTo(methodOn(QuestionSetController.class).getQuestionSetById(questionSetDto.getId())).withSelfRel());
        return questionSetDto;
    }

    @PatchMapping
    public QuestionSetDto updateQuestionSet(@RequestBody UpdateQuestionSetRequest updateRequest) {
        QuestionSet updatedQuestionSet = questionSetService.update(updateRequest);
        QuestionSetDto questionSetDto = questionSetMapper.toDto(updatedQuestionSet);

        questionSetDto.add(linkTo(methodOn(QuestionSetController.class).getQuestionSetById(questionSetDto.getId())).withSelfRel());
        return questionSetDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestionSetById(@PathVariable Long id) {
        try {
            questionSetService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceReferencedFromTables("QuestionSet exists in another tables. %s"
                    .formatted(e.getCause().getCause().getMessage().split(System.lineSeparator())[1]));
        }
    }

    @PatchMapping("/{id}/quizzes")
    public QuestionSetDto removeQuizzesFromQuestionSet(@PathVariable Long id, @RequestBody List<Long> ids) {
        QuestionSet updatedQuestionSet = questionSetService.removeQuizzes(id, ids);
        QuestionSetDto questionSetDto = questionSetMapper.toDto(updatedQuestionSet);

        questionSetDto.add(linkTo(methodOn(QuestionSetController.class).removeQuizzesFromQuestionSet(id, ids)).withSelfRel());
        return questionSetDto;
    }

    @PatchMapping("/{id}/questions")
    public QuestionSetDto removeQuestionsFromQuestionSet(@PathVariable Long id, @RequestBody List<Long> ids) {
        QuestionSet updatedQuestionSet = questionSetService.removeQuestions(id, ids);
        QuestionSetDto questionSetDto = questionSetMapper.toDto(updatedQuestionSet);

        questionSetDto.add(linkTo(methodOn(QuestionSetController.class).removeQuestionsFromQuestionSet(id, ids)).withSelfRel());
        return questionSetDto;
    }
}
