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
@Relation(itemRelation = "quizAnswer", collectionRelation = "quizAnswers")
public class QuizAnswerDto extends RepresentationModel<QuizAnswerDto> {

    private Long id;
    private String content;
    private Integer sequence;
    private Boolean correct;
    private QuizDto quiz;
}
