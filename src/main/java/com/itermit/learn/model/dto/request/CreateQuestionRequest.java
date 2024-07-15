package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.AnswerDto;
import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.SourceDto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionRequest {

    private String title;
    private String content;
    private SourceDto source;
    private CategoryDto category;
    private AnswerDto answer;
}
