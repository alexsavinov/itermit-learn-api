package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.QuestionDto;
import com.itermit.learn.model.dto.QuizDto;
import com.itermit.learn.model.dto.UserDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionSetRequest {

    private Boolean custom;
    private String name;
    private CategoryDto category;
    private UserDto user;
    private List<QuizDto> quizzes;
    private List<QuestionDto> questions;
}
