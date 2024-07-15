package com.itermit.learn.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "question", collectionRelation = "questions")
public class QuestionDto extends RepresentationModel<QuestionDto> {

    private Long id;
    private String title;
    private String content;
    private SourceDto source;
    private CategoryDto category;
    private AnswerDto answer;
    private Instant createdDate;
    private Instant lastUpdateDate;
}
