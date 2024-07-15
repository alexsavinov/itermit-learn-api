package com.itermit.learn.service.mapper;

import com.itermit.learn.model.dto.AnswerDto;
import com.itermit.learn.model.entity.Answer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class AnswerMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public AnswerDto toDto(Answer answer) {
        return modelMapper.map(answer, AnswerDto.class);
    }

    public AnswerDto toIdDto(Answer answer) {
        return AnswerDto.builder().id(answer.getId()).build();
    }

    public AnswerDto toBasicDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .build();
    }

    public Answer toAnswer(AnswerDto answer) {
        return modelMapper.map(answer, Answer.class);
    }
}
