package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.*;
import com.itermit.learn.model.dto.request.CreateQuestionSetRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionSetRequest;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class QuestionSetMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserMapper userMapper = new UserMapper();
    private final QuizMapper quizMapper = new QuizMapper();
    private final QuestionMapper questionMapper = new QuestionMapper();

    {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Converter<User, UserDto> converterUserToDto = c -> userMapper.toBasicDto(c.getSource());
        Converter<UserDto, User> converterDtoToUser = c -> userMapper.toUser(c.getSource());
        Converter<List<Quiz>, List<QuizDto>> converterQuizzes =
                c -> c.getSource().stream().map(quizMapper::toDto).toList();
        Converter<List<Question>, List<QuestionDto>> converterQuestions =
                c -> c.getSource().stream().map(questionMapper::toBasicDto).toList();

        /* DTO */
        modelMapper.emptyTypeMap(QuestionSet.class, QuestionSetDto.class).addMappings(m -> {
            m.skip(QuestionSetDto::setUser);
            m.when(Conditions.isNotNull()).using(converterUserToDto)
                    .map(QuestionSet::getUser, QuestionSetDto::setUser);
            m.when(Conditions.isNotNull()).using(converterQuizzes)
                    .map(QuestionSet::getQuizzes, QuestionSetDto::setQuizzes);
            m.when(Conditions.isNotNull()).using(converterQuestions)
                    .map(QuestionSet::getQuestions, QuestionSetDto::setQuestions);
        }).implicitMappings();

        /* Basic DTO */
        modelMapper.emptyTypeMap(QuestionSet.class, QuestionSetDto.class, "BasicDto").addMappings(m -> {
            m.skip(QuestionSetDto::setQuestions);
            m.skip(QuestionSetDto::setQuizzes);
            m.skip(QuestionSetDto::setUser);
            m.skip(QuestionSetDto::setCreatedDate);
            m.skip(QuestionSetDto::setLastUpdateDate);
            m.using(converterUserToDto).map(QuestionSet::getUser, QuestionSetDto::setUser);
        }).implicitMappings();

        /* Update Request */
        modelMapper.emptyTypeMap(UpdateQuestionSetRequest.class, QuestionSet.class).addMappings(m -> {
            m.skip(QuestionSet::setUser);
            m.using(converterDtoToUser).map(UpdateQuestionSetRequest::getUser, QuestionSet::setUser);
        }).implicitMappings();

        /* Entity */
        modelMapper.emptyTypeMap(QuestionSetDto.class, QuestionSet.class).addMappings(m -> {
            m.skip(QuestionSet::setUser);
            m.using(converterDtoToUser).map(QuestionSetDto::getUser, QuestionSet::setUser);
        }).implicitMappings();
    }

    public QuestionSetDto toDto(QuestionSet questionSet) {
        return modelMapper.map(questionSet, QuestionSetDto.class);
    }

    public QuestionSetDto toIdDto(QuestionSet questionSet) {
        return QuestionSetDto.builder().id(questionSet.getId()).build();
    }

    public QuestionSetDto toBasicDto(QuestionSet questionSet) {
        return modelMapper.map(questionSet, QuestionSetDto.class, "BasicDto");
    }

    public QuestionSet toQuestionSet(CreateQuestionSetRequest request) {
        return modelMapper.map(request, QuestionSet.class);
    }

    public QuestionSet toQuestionSet(QuestionSetDto dto) {
        return modelMapper.map(dto, QuestionSet.class);
    }

    public void toQuestionSet(UpdateQuestionSetRequest request, QuestionSet questionSet) {
        modelMapper.map(request, questionSet);
    }
}
