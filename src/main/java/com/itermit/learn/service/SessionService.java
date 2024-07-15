package com.itermit.learn.service;

import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.UserAnswerDto;
import com.itermit.learn.model.dto.request.CreateSessionRequest;
import com.itermit.learn.model.dto.request.StartSessionRequest;
import com.itermit.learn.model.dto.request.UpdateSessionRequest;
import com.itermit.learn.model.entity.Session;

import java.util.List;


public interface SessionService extends PageableSessionService {

    Session findById(Long id);

    Session create(CreateSessionRequest createRequest);

    Session update(UpdateSessionRequest updateRequest);

    Session addQuizAnswer(Long id, QuizAnswerDto request);

    Session removeQuizAnswers(Long id, List<Long> quizAnswerId);

    Session removeUserAnswers(Long id, List<Long> userAnswerId);

    Session addUserAnswer(Long id, UserAnswerDto request);

    Session start(StartSessionRequest request);

    Session finish(Long id);

    void delete(Long id);
}
