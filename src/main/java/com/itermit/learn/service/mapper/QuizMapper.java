package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.QuizDto;
import com.itermit.learn.model.dto.SourceDto;
import com.itermit.learn.model.dto.request.CreateQuizRequest;
import com.itermit.learn.model.dto.request.UpdateQuizRequest;
import com.itermit.learn.model.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Optional.ofNullable;


@Component
@AllArgsConstructor
public class QuizMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final QuizAnswerMapper quizAnswerMapper = new QuizAnswerMapper(this);

    {
        Converter<List<QuizAnswer>, List<QuizAnswerDto>> converterQuizAnswersToDto =
                c -> c.getSource().stream().map(quizAnswerMapper::toDto).toList();
        Converter<List<QuizAnswerDto>, List<QuizAnswer>> converterDtoToQuizAnswers = c ->
                replaceQuizAnswers(c.getDestination(), c.getSource());
        Converter<CategoryDto, Category> converterCategory = c -> modelMapper.map(c.getSource(), Category.class);
        Converter<SourceDto, Source> converterSource = c -> modelMapper.map(c.getSource(), Source.class);

        /* DTO */
        modelMapper.createTypeMap(Quiz.class, QuizDto.class).addMappings(m -> {
            m.when(Conditions.isNotNull()).using(converterQuizAnswersToDto)
                    .map(Quiz::getQuizAnswers, QuizDto::setQuizAnswers);
        });

        /* Basic DTO */
        modelMapper.createTypeMap(Quiz.class, QuizDto.class, "BasicDto").addMappings(m -> {
            m.skip(Quiz::getQuizAnswers, QuizDto::setQuizAnswers);
            m.skip(Quiz::getCreatedDate, QuizDto::setCreatedDate);
            m.skip(Quiz::getLastUpdateDate, QuizDto::setLastUpdateDate);
        });

        /* Update Request */
        modelMapper.emptyTypeMap(UpdateQuizRequest.class, Quiz.class).addMappings(m -> {
            m.skip(Quiz::setCategory);
            m.skip(Quiz::setSource);
            m.skip(Quiz::setQuizAnswers);
            m.when(Conditions.isNotNull()).using(converterCategory)
                    .map(UpdateQuizRequest::getCategory, Quiz::setCategory);
            m.when(Conditions.isNotNull()).using(converterSource)
                    .map(UpdateQuizRequest::getSource, Quiz::setSource);
            m.when(Conditions.isNotNull()).using(converterDtoToQuizAnswers)
                    .map(UpdateQuizRequest::getQuizAnswers, Quiz::setQuizAnswers);
        }).implicitMappings();
    }

    public QuizDto toDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }

    public QuizDto toIdDto(Quiz quiz) {
        return QuizDto.builder().id(quiz.getId()).build();
    }

    public QuizDto toBasicDto(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class, "BasicDto");
    }

    public Quiz toQuiz(CreateQuizRequest request) {
        return modelMapper.map(request, Quiz.class);
    }

    public void toQuiz(UpdateQuizRequest request, Quiz quiz) {
        modelMapper.map(request, quiz);
        ofNullable(request.getQuizAnswers()).ifPresent(a -> quiz.setLastUpdateDate());
    }

    private List<QuizAnswer> replaceQuizAnswers(List<QuizAnswer> existedAnswers, List<QuizAnswerDto> answers) {
        List<QuizAnswer> parsedAnswers = modelMapper.map(answers, new TypeToken<List<QuizAnswer>>() {}.getType());

        if (existedAnswers.size() > parsedAnswers.size()) {
            existedAnswers.subList(parsedAnswers.size(), existedAnswers.size()).clear();
        }

        for (int i = 0; i < parsedAnswers.size(); i++) {
            if (i >= existedAnswers.size()) {
                existedAnswers.add(parsedAnswers.get(i));
            } else {
                existedAnswers.get(i).setContent(parsedAnswers.get(i).getContent());
                existedAnswers.get(i).setSequence(parsedAnswers.get(i).getSequence());
                existedAnswers.get(i).setCorrect(parsedAnswers.get(i).isCorrect());
            }
        }

        return existedAnswers;
    }
}
