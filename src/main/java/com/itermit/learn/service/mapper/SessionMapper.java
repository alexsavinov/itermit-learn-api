package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.*;
import com.itermit.learn.model.dto.request.CreateSessionRequest;
import com.itermit.learn.model.dto.request.UpdateSessionRequest;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class SessionMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final QuestionSetMapper questionSetMapper = new QuestionSetMapper();
    private final UserAnswerMapper userAnswerMapper = new UserAnswerMapper();
    private final QuizAnswerMapper quizAnswerMapper = new QuizAnswerMapper(new QuizMapper());
    private final UserMapper userMapper = new UserMapper();

    {
        Converter<User, UserDto> converterUserDto = c -> userMapper.toBasicDto(c.getSource());
        Converter<UserDto, User> converterDtoToUser = c -> userMapper.toUser(c.getSource());
        Converter<QuestionSet, QuestionSetDto> converterQuestionSetDto =
                c -> questionSetMapper.toDto(c.getSource());
        Converter<QuestionSet, QuestionSetDto> converterQuestionSetBasicDto =
                c -> questionSetMapper.toBasicDto(c.getSource());
        Converter<QuestionSetDto, QuestionSet> converterDtoToQuestionSet =
                c -> questionSetMapper.toQuestionSet(c.getSource());
        Converter<Set<QuizAnswer>, List<QuizAnswerDto>> converterQuizAnswersDto = c ->
                c.getSource().stream().map(quizAnswerMapper::toDto).toList();
        Converter<Set<QuizAnswer>, List<QuizAnswerDto>> converterQuizAnswersIdDto = c ->
                c.getSource().stream().map(quizAnswerMapper::toIdDto).toList();
        Converter<List<QuizAnswerDto>, Set<QuizAnswer>> converterDtoToQuizAnswers = c ->
                c.getSource().stream().map(quizAnswerMapper::toQuizAnswer).collect(Collectors.toSet());
        Converter<Set<UserAnswer>, List<UserAnswerDto>> converterUserAnswersDto = c ->
                c.getSource().stream().map(userAnswerMapper::toDto).toList();
        Converter<Set<UserAnswer>, List<UserAnswerDto>> converterUserAnswersIdDto = c ->
                c.getSource().stream().map(userAnswerMapper::toIdDto).toList();

        /* DTO */
        modelMapper.emptyTypeMap(Session.class, SessionDto.class).addMappings(m -> {
            m.skip(SessionDto::setQuizAnswers);
            m.skip(SessionDto::setUserAnswers);
            m.skip(SessionDto::setQuestionSet);
            m.skip(SessionDto::setUser);
            m.when(Conditions.isNotNull()).using(converterUserDto)
                    .map(Session::getUser, SessionDto::setUser);
            m.when(Conditions.isNotNull()).using(converterQuestionSetDto)
                    .map(Session::getQuestionSet, SessionDto::setQuestionSet);
            m.when(Conditions.isNotNull()).using(converterQuizAnswersDto)
                    .map(Session::getQuizAnswers, SessionDto::setQuizAnswers);
            m.when(Conditions.isNotNull()).using(converterUserAnswersDto)
                    .map(Session::getUserAnswers, SessionDto::setUserAnswers);
        }).implicitMappings();

        /* Basic DTO */
        modelMapper.emptyTypeMap(Session.class, SessionDto.class, "BasicDto").addMappings(m -> {
            m.skip(SessionDto::setQuizAnswers);
            m.skip(SessionDto::setUserAnswers);
            m.skip(SessionDto::setQuestionSet);
            m.skip(SessionDto::setUser);
            m.when(Conditions.isNotNull()).using(converterUserDto)
                    .map(Session::getUser, SessionDto::setUser);
            m.when(Conditions.isNotNull()).using(converterQuestionSetBasicDto)
                    .map(Session::getQuestionSet, SessionDto::setQuestionSet);
            m.when(Conditions.isNotNull()).using(converterQuizAnswersIdDto)
                    .map(Session::getQuizAnswers, SessionDto::setQuizAnswers);
            m.when(Conditions.isNotNull()).using(converterUserAnswersIdDto)
                    .map(Session::getUserAnswers, SessionDto::setUserAnswers);
        }).implicitMappings();

        /* Update Request */
        modelMapper.emptyTypeMap(UpdateSessionRequest.class, Session.class).addMappings(m -> {
            m.skip(Session::setQuizAnswers);
            m.skip(Session::setUserAnswers);
            m.skip(Session::setQuestionSet);
            m.skip(Session::setUser);
            m.when(Conditions.isNotNull()).using(converterDtoToUser)
                    .map(UpdateSessionRequest::getUser, Session::setUser);
            m.when(Conditions.isNotNull()).using(converterDtoToQuestionSet)
                    .map(UpdateSessionRequest::getQuestionSet, Session::setQuestionSet);
            m.when(Conditions.isNotNull()).using(converterDtoToQuizAnswers)
                    .map(UpdateSessionRequest::getQuizAnswers, Session::setQuizAnswers);
        }).implicitMappings();
    }

    public SessionDto toDto(Session session) {
        return modelMapper.map(session, SessionDto.class);
    }

    public SessionDto toBasicDto(Session session) {
        return modelMapper.map(session, SessionDto.class, "BasicDto");
    }

    public SessionDto toIdDto(Session session) {
        return SessionDto.builder().id(session.getId()).build();
    }

    public Session toSession(CreateSessionRequest request) {
        return modelMapper.map(request, Session.class);
    }

    public void toSession(UpdateSessionRequest request, Session session) {
        modelMapper.map(request, session);
    }
}
