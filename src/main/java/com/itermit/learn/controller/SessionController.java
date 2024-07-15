package com.itermit.learn.controller;

import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.SessionDto;
import com.itermit.learn.model.dto.SessionsDto;
import com.itermit.learn.model.dto.UserAnswerDto;
import com.itermit.learn.model.dto.request.CreateSessionRequest;
import com.itermit.learn.model.dto.request.StartSessionRequest;
import com.itermit.learn.model.dto.request.UpdateSessionRequest;
import com.itermit.learn.model.entity.Session;
import com.itermit.learn.service.SessionService;
import com.itermit.learn.service.mapper.SessionMapper;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;
    private final SessionsDto sessionsDto;
    private final PagedResourcesAssembler<Session> pagedResourcesAssembler;

    @GetMapping("/{id}")
    public SessionDto getSessionById(@PathVariable Long id) {
        Session foundSession = sessionService.findById(id);
        SessionDto sessionDto = sessionMapper.toDto(foundSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).getSessionById(sessionDto.getId())).withSelfRel());
        return sessionDto;
    }

    @GetMapping
    public PagedModel<SessionDto> getSessions(
            Pageable pageable, @RequestParam(required = false) Map<String, String> params) {
        Page<Session> foundSessions = sessionService.findAll(pageable, params);

        return pagedResourcesAssembler.toModel(foundSessions, sessionsDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto addSession(@RequestBody CreateSessionRequest createRequest) {
        Session createdSession = sessionService.create(createRequest);
        SessionDto sessionDto = sessionMapper.toDto(createdSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).addSession(createRequest)).withSelfRel());
        return sessionDto;
    }

    @PatchMapping
    public SessionDto updateSession(@RequestBody UpdateSessionRequest updateRequest) {
        Session updatedSession = sessionService.update(updateRequest);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).updateSession(updateRequest)).withSelfRel());
        return sessionDto;
    }

    @PostMapping("/{id}/quiz-answer")
    public SessionDto addQuizAnswerToSession(@PathVariable Long id, @RequestBody QuizAnswerDto answer) {
        Session updatedSession = sessionService.addQuizAnswer(id, answer);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).addQuizAnswerToSession(id, answer)).withSelfRel());
        return sessionDto;
    }

    @PatchMapping("/{id}/quiz-answer")
    public SessionDto removeQuizAnswerFromSession(@PathVariable Long id, @RequestBody List<Long> answerIds) {
        Session updatedSession = sessionService.removeQuizAnswers(id, answerIds);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).removeQuizAnswerFromSession(id, answerIds)).withSelfRel());
        return sessionDto;
    }

    @PostMapping("/{id}/user-answer")
    public SessionDto addUserAnswerToSession(@PathVariable Long id, @RequestBody UserAnswerDto answer) {
        Session updatedSession = sessionService.addUserAnswer(id, answer);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).addUserAnswerToSession(id, answer)).withSelfRel());
        return sessionDto;
    }

    @PatchMapping("/{id}/user-answer")
    public SessionDto removeUserAnswerFromSession(@PathVariable Long id, @RequestBody List<Long> answerIds) {
        Session updatedSession = sessionService.removeUserAnswers(id, answerIds);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).removeUserAnswerFromSession(id, answerIds)).withSelfRel());
        return sessionDto;
    }

    @PostMapping("/start")
    public SessionDto startSession(@RequestBody StartSessionRequest request) {
        Session updatedSession = sessionService.start(request);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).startSession(request)).withSelfRel());
        return sessionDto;
    }

    @PatchMapping("/{id}/finish")
    public SessionDto finishSession(@PathVariable Long id) {
        Session updatedSession = sessionService.finish(id);
        SessionDto sessionDto = sessionMapper.toDto(updatedSession);

        sessionDto.add(linkTo(methodOn(SessionController.class).finishSession(id)).withSelfRel());
        return sessionDto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSessionById(@PathVariable Long id) {
        sessionService.delete(id);
    }

}
