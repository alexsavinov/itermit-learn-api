package com.itermit.learn.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "session", collectionRelation = "sessions")
public class SessionDto extends RepresentationModel<SessionDto> {

    private Long id;
    private UserDto user;
    private QuestionSetDto questionSet;
    private List<QuizAnswerDto> quizAnswers;
    private List<UserAnswerDto> userAnswers;
    private Instant finishedDate;
    private Instant createdDate;
    private Instant lastUpdateDate;
}
