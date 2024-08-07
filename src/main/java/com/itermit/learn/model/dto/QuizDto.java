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
@Relation(itemRelation = "quiz", collectionRelation = "quizzes")
public class QuizDto extends RepresentationModel<QuizDto> {

    private Long id;
    private String title;
    private String content;
    private SourceDto source;
    private CategoryDto category;
    private List<QuizAnswerDto> quizAnswers;
    private Instant createdDate;
    private Instant lastUpdateDate;
}
