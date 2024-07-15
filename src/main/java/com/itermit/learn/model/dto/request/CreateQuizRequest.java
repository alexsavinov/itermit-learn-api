package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.SourceDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuizRequest {

    private String title;
    private String content;
    private SourceDto source;
    private CategoryDto category;
    private List<QuizAnswerDto> quizAnswers;
}
