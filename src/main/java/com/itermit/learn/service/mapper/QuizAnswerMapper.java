package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.QuizDto;
import com.itermit.learn.model.entity.Quiz;
import com.itermit.learn.model.entity.QuizAnswer;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class QuizAnswerMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private QuizMapper quizMapper;

    {
        Converter<Quiz, QuizDto> converterQuizToDto = c -> quizMapper.toIdDto(c.getSource());

        /* DTO */
        modelMapper.emptyTypeMap(QuizAnswer.class, QuizAnswerDto.class).addMappings(m -> {
            m.skip(QuizAnswerDto::setQuiz);
            m.when(Conditions.isNotNull()).using(converterQuizToDto)
                    .map(QuizAnswer::getQuiz, QuizAnswerDto::setQuiz);
        }).implicitMappings();

        /* Basic DTO */
        modelMapper.emptyTypeMap(QuizAnswer.class, QuizAnswerDto.class, "BasicDto").addMappings(m -> {
            m.skip(QuizAnswerDto::setQuiz);
        }).implicitMappings();
    }

    @Autowired
    public void setQuizMapper(@Lazy QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    public QuizAnswerDto toDto(QuizAnswer quizAnswer) {
        return modelMapper.map(quizAnswer, QuizAnswerDto.class);
    }

    public QuizAnswerDto toBasicDto(QuizAnswer quizAnswer) {
        return modelMapper.map(quizAnswer, QuizAnswerDto.class, "BasicDto");
    }

    public QuizAnswerDto toIdDto(QuizAnswer quizAnswer) {
        return QuizAnswerDto.builder().id(quizAnswer.getId()).build();
    }

    public QuizAnswer toQuizAnswer(QuizAnswerDto dto) {
        return modelMapper.map(dto, QuizAnswer.class);
    }
}
