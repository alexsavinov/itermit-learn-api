package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuestionSetRequest {

    private Long id;
    private String name;
    private Boolean custom;
    private CategoryDto category;
    private UserDto user;
    private List<QuizDto> quizzes;
    private List<QuestionDto> questions;
}
