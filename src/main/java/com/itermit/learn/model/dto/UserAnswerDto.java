package com.itermit.learn.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "userAnswer", collectionRelation = "userAnswers")
public class UserAnswerDto extends RepresentationModel<UserAnswerDto> {

    private Long id;
    private String content;
    private SessionDto session;
    private QuestionDto question;
}
