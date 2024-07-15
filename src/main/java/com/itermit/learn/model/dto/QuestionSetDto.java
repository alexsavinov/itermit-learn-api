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
@Relation(itemRelation = "questionSet", collectionRelation = "questionSets")
public class QuestionSetDto extends RepresentationModel<QuestionSetDto> {

    private Long id;
    private String name;
    private Boolean custom;
    private CategoryDto category;
    private UserDto user;
    private List<QuizDto> quizzes;
    private List<QuestionDto> questions;
    private Instant createdDate;
    private Instant lastUpdateDate;
}
