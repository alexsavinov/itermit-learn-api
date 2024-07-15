package com.itermit.learn.service.implementation;

import com.itermit.learn.exception.ResourceNotFoundException;
import com.itermit.learn.exception.SessionAnotherStartedException;
import com.itermit.learn.exception.SessionFinishedException;
import com.itermit.learn.model.EMode;
import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.SourceDto;
import com.itermit.learn.model.dto.UserAnswerDto;
import com.itermit.learn.model.dto.request.CreateSessionRequest;
import com.itermit.learn.model.dto.request.StartSessionRequest;
import com.itermit.learn.model.dto.request.UpdateSessionRequest;
import com.itermit.learn.model.entity.*;
import com.itermit.learn.repository.*;
import com.itermit.learn.repository.specification.SessionSpecs;
import com.itermit.learn.service.QuestionService;
import com.itermit.learn.service.QuizService;
import com.itermit.learn.service.SessionService;
import com.itermit.learn.service.mapper.SessionMapper;
import com.itermit.learn.service.mapper.UserAnswerMapper;
import com.itermit.learn.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Optional.ofNullable;


@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final PageableSessionRepository pageableSessionRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionService questionService;
    private final QuestionSetRepository questionSetRepository;
    private final QuizRepository quizRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuestionRepository questionRepository;
    private final SessionMapper sessionMapper;
    private final ModelMapper modelMapper;
    private final UserAnswerMapper userAnswerMapper;


    @Override
    public Session findById(Long id) {
        log.debug("Looking for an session with id {}", id);

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested resource not found (id = %s)"
                        .formatted(id)
                ));

        log.info("Retrieved an session with id {}", id);
        return session;
    }

    @Override
    public Page<Session> findAll(Pageable pageable, Map<String, String> params) {
        log.debug("Retrieving sessions. Page request: {}", pageable);

        Page<Session> sessions = pageableSessionRepository.findAll(SessionSpecs.filter(params), pageable);

        log.info("Retrieved {} sessions of {} total", sessions.getSize(), sessions.getTotalElements());
        return sessions;
    }

    @Transactional
    @Override
    public Session create(CreateSessionRequest createRequest) {
        log.debug("Creating a new session");

        List<Session> startedSessions = sessionRepository
                .findAllByUserIdAndFinishedDateIsNull(createRequest.getUser().getId());

        if (!startedSessions.isEmpty()) {
            throw new SessionAnotherStartedException("Another sessions already started (%s)"
                    .formatted(startedSessions.stream().map(Session::getId).toList()));
        }

        Session newSession = sessionMapper.toSession(createRequest);
        Session createdSession = sessionRepository.save(newSession);

        log.info("Created a new session with id {}", createdSession.getId());
        return createdSession;
    }

    @Transactional
    @Override
    public Session update(UpdateSessionRequest updateRequest) {
        log.debug("Updating session");

        Session foundSession = findById(updateRequest.getId());

        ofNullable(updateRequest.getUserAnswers()).ifPresent(userAnswers ->
        {
            Set<UserAnswer> userAnswersSet = new HashSet<>();
            userAnswers.forEach(userAnswerDto -> {
                UserAnswer userAnswer = userAnswerMapper.toUserAnswer(userAnswerDto);
                userAnswer.setQuestion(questionService.findById(userAnswerDto.getQuestion().getId()));
                userAnswer.setSession(foundSession);
                userAnswersSet.add(userAnswerRepository.save(userAnswer));
            });
            foundSession.setUserAnswers(userAnswersSet);
        });

        sessionMapper.toSession(updateRequest, foundSession);

        Session updatedSession = sessionRepository.save(foundSession);

        log.info("Updated an session with id {}", updatedSession.getId());
        return updatedSession;
    }

    @Transactional
    @Override
    public Session addQuizAnswer(Long id, QuizAnswerDto answer) {
        log.debug("Adding QuizAnswer to the session");

        Session foundSession = findById(id);

        Set<QuizAnswer> answers = foundSession.getQuizAnswers();
//        if (answers.stream().anyMatch(i -> i.getId().equals(answer.getId()))) {
//            throw new SessionAnswerExistException(
//                    "QuizAnswer (id = %s) already exists in session (id = %s)".formatted(
//                            answer.getId(), id));
//        }

        if (answers.removeIf(quizAnswer -> quizAnswer.getQuiz().getId().equals(answer.getQuiz().getId()))) {
            log.info("Removed QuizAnswer (id={}) for Quiz (id={}) from session with id {}",
                    answer.getId(), answer.getQuiz().getId(), id);
        }
        ;
        answers.add(quizAnswerRepository.findById(answer.getId()).get());

        Session updatedSession = sessionRepository.save(foundSession);

        log.info("Added QuizAnswer (id={}) for Quiz (id={}) to session with id {}",
                answer.getId(), answer.getQuiz().getId(), id);
        return updatedSession;
    }

    @Transactional
    @Override
    public Session removeQuizAnswers(Long id, List<Long> quizAnswerIds) {
        log.debug("Removing QuizAnswer from the session");

        Session foundSession = findById(id);

        foundSession.getQuizAnswers().removeIf(a -> quizAnswerIds.contains(a.getId()));

        Session updatedSession = sessionRepository.save(foundSession);

        log.info("Removed QuizAnswer (id={}) from session with id {}", quizAnswerIds, id);
        return updatedSession;
    }

    @Transactional
    @Override
    public Session addUserAnswer(Long id, UserAnswerDto answer) {
        log.debug("Adding UserAnswer to the session");

        Session foundSession = findById(id);

        Set<UserAnswer> answers = foundSession.getUserAnswers();

        Long questionId = answer.getQuestion().getId();

//        if (userAnswerRepository.existsBySessionIdAndQuestionId(id, questionId)) {
//            throw new SessionAnswerExistException(
//                    "UserAnswer (Question id = %s) already exists in session (id = %s)".formatted(
//                            questionId, id));
//        }
        userAnswerRepository.findBySessionIdAndQuestionId(id, questionId).ifPresentOrElse(
                userAnswer -> {
                    userAnswer.setContent(answer.getContent());
                    log.warn("Updating UserAnswer(%s), because Question(%s) already answered in session (id = %s)"
                            .formatted(userAnswer.getId(), questionId, id));
                    userAnswerRepository.save(userAnswer);
                },
                () -> {
                    UserAnswer userAnswer = modelMapper.map(answer, UserAnswer.class);
                    userAnswer.setQuestion(questionService.findById(questionId));
                    userAnswerRepository.save(userAnswer);
                    log.info("Added UserAnswer (Question id = {}) to session with id {}", questionId, id);
                });

        return sessionRepository.save(foundSession);
    }

    @Transactional
    @Override
    public Session removeUserAnswers(Long id, List<Long> userAnswerIds) {
        log.debug("Removing UserAnswers from the session");

        userAnswerRepository.deleteAllByIdInBatch(userAnswerIds);

        log.info("Removed UserAnswers (id={}) from session with id {}", userAnswerIds, id);
        return findById(id);
    }

    @Transactional
    @Override
    public Session finish(Long id) {
        log.debug("Finishing session");

        Session foundSession = findById(id);

        if (ofNullable(foundSession.getFinishedDate()).isPresent()) {
            throw new SessionFinishedException(
                    "Session already finished at %s (id = %s)".formatted(
                            foundSession.getFinishedDate(), foundSession.getId()));
        }

        foundSession.setFinishedDate(Instant.now());

        Session updatedSession = sessionRepository.save(foundSession);

        log.info("Finished session with id {}", updatedSession.getId());
        return updatedSession;
    }

    @Transactional
    @Override
    public Session start(StartSessionRequest request) {
        log.debug("Starting session");

//        List<Session> startedSessions = sessionRepository
//                .findAllByUserIdAndFinishedDateIsNull(request.getUser().getId());
//
//        if (!startedSessions.isEmpty()) {
//            throw new SessionAnotherStartedException("Another sessions already started (%s)"
//                    .formatted(startedSessions.stream().map(Session::getId).toList()));
//        }

        User user = User.builder().id(request.getUser().getId()).build();

        Session.SessionBuilder sessionBuilder = Session.builder().user(user);

        ofNullable(request.getQuestionSet()).ifPresentOrElse(
                sessionBuilder::questionSet,
                () -> {
                    QuestionSet.QuestionSetBuilder qsBuilder = QuestionSet.builder()
                            .name("autogenerated")
                            .custom(false)
                            .user(user);

                    ofNullable(request.getCategory()).ifPresent(a ->
                            qsBuilder.category(Category.builder().id(a.getId()).build()));

                    EMode mode = ofNullable(request.getMode()).orElse(EMode.MIXED);
                    int totalItems = 10;
                    if (!request.getTotalItems().equals(0)) {
                        totalItems = request.getTotalItems();
                    }
                    final Long categoryId = ofNullable(request.getCategory()).orElse(CategoryDto.builder().id(0L).build()).getId();
                    final Long sourceId = ofNullable(request.getSource()).orElse(SourceDto.builder().id(0L).build()).getId();

                    switch (mode) {
                        case QUIZZES -> qsBuilder.quizzes(quizRepository.findRandom(categoryId, sourceId, totalItems));
                        case QUESTIONS ->
                                qsBuilder.questions(questionRepository.findRandom(categoryId, sourceId, totalItems));
                        default -> {
                            qsBuilder.quizzes(quizRepository.findRandom(categoryId, sourceId, totalItems / 2));
                            qsBuilder.questions(questionRepository.findRandom(categoryId, sourceId, totalItems - totalItems / 2));
                        }
                    }
                    QuestionSet questionSet = questionSetRepository.save(qsBuilder.build());
                    sessionBuilder.questionSet(questionSet);
                }
        );


        Session createdSession = sessionRepository.save(sessionBuilder.build());

        log.info("Started session with id {}", createdSession.getId());
        return createdSession;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting session with id {}", id);

        Session foundSession = findById(id);

        sessionRepository.delete(foundSession);

        log.info("Session with id {} is deleted", foundSession.getId());
    }

}
