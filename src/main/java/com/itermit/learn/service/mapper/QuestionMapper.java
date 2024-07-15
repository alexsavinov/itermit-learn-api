package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.*;
import com.itermit.learn.model.dto.request.CreateQuestionRequest;
import com.itermit.learn.model.dto.request.UpdateQuestionRequest;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class QuestionMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final AnswerMapper answerMapper = new AnswerMapper();

    {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Converter<Answer, AnswerDto> converter = c -> answerMapper.toBasicDto(c.getSource());
        Converter<CategoryDto, Category> converterCategory = c -> modelMapper.map(c.getSource(), Category.class);
        Converter<SourceDto, Source> converterSource = c -> modelMapper.map(c.getSource(), Source.class);

        /* DTO */
        modelMapper.emptyTypeMap(Question.class, QuestionDto.class).addMappings(m -> {
            m.skip(QuestionDto::setAnswer);
            m.when(Conditions.isNotNull()).using(converter).map(Question::getAnswer, QuestionDto::setAnswer);
        }).implicitMappings();

        /* Basic DTO */
        modelMapper.emptyTypeMap(Question.class, QuestionDto.class, "BasicDto").addMappings(m -> {
            m.skip(QuestionDto::setAnswer);
            m.when(Conditions.isNotNull()).using(converter).map(Question::getAnswer, QuestionDto::setAnswer);
        }).implicitMappings();

        /* Update Request */
        modelMapper.emptyTypeMap(UpdateQuestionRequest.class, Question.class).addMappings(m -> {
            m.skip(Question::setCategory);
            m.skip(Question::setSource);
            m.when(Conditions.isNotNull()).using(converterCategory)
                    .map(UpdateQuestionRequest::getCategory, Question::setCategory);
            m.when(Conditions.isNotNull()).using(converterSource)
                    .map(UpdateQuestionRequest::getSource, Question::setSource);
        }).implicitMappings();
    }

    public QuestionDto toDto(Question quiz) {
        return modelMapper.map(quiz, QuestionDto.class);
    }

    public QuestionDto toIdDto(Question question) {
        return QuestionDto.builder().id(question.getId()).build();
    }

    public QuestionDto toBasicDto(Question question) {
        return modelMapper.map(question, QuestionDto.class, "BasicDto");
    }

    public Question toQuestion(CreateQuestionRequest request) {
        return modelMapper.map(request, Question.class);
    }

    public void toQuestion(UpdateQuestionRequest request, Question question) {
        modelMapper.map(request, question);
    }
}
