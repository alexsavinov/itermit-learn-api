package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.QuestionSetDto;
import com.itermit.learn.model.dto.QuizAnswerDto;
import com.itermit.learn.model.dto.UserAnswerDto;
import com.itermit.learn.model.dto.UserDto;
import lombok.*;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSessionRequest {

    private Long id;
    private UserDto user;
    private QuestionSetDto questionSet;
    private Instant finishedDate;
    private List<QuizAnswerDto> quizAnswers;
    private List<UserAnswerDto> userAnswers;
}
