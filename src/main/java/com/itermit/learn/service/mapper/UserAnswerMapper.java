package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.*;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserAnswerMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final QuestionMapper questionMapper = new QuestionMapper();

    {
        Converter<Question, QuestionDto> converterQuestion = c -> questionMapper.toIdDto(c.getSource());
        Converter<Session, SessionDto> converterSession = c -> SessionDto.builder()
                .id(c.getSource().getId())
                .build();

        /* DTO */
        modelMapper.emptyTypeMap(UserAnswer.class, UserAnswerDto.class).addMappings(m -> {
            m.skip(UserAnswerDto::setQuestion);
            m.skip(UserAnswerDto::setSession);
            m.using(converterQuestion)
                    .map(UserAnswer::getQuestion, UserAnswerDto::setQuestion);
            m.using(converterSession)
                    .map(UserAnswer::getSession, UserAnswerDto::setSession);
        }).implicitMappings();

        /* Basic DTO */
        modelMapper.emptyTypeMap(UserAnswer.class, UserAnswerDto.class, "BasicDto").addMappings(m -> {
            m.skip(UserAnswerDto::setQuestion);
            m.skip(UserAnswerDto::setSession);
        }).implicitMappings();

        /* Entity */
        modelMapper.emptyTypeMap(UserAnswerDto.class, UserAnswer.class).addMappings(m -> {
            m.skip(UserAnswer::setSession);
            m.skip(UserAnswer::setQuestion);
//            m.using(converterDtoToUser).map(QuestionSetDto::getUser, QuestionSet::setUser);
        }).implicitMappings();
    }

    public UserAnswerDto toDto(UserAnswer entity) {
        return modelMapper.map(entity, UserAnswerDto.class);
    }

    public UserAnswerDto toIdDto(UserAnswer entity) {
        return UserAnswerDto.builder().id(entity.getId()).build();
    }

    public UserAnswerDto toBasicDto(UserAnswer entity) {
        return modelMapper.map(entity, UserAnswerDto.class, "BasicDto");
    }

    public UserAnswer toUserAnswer(UserAnswerDto dto) {
        return modelMapper.map(dto, UserAnswer.class);
    }

}
