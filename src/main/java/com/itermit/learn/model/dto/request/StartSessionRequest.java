package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.EMode;
import com.itermit.learn.model.dto.CategoryDto;
import com.itermit.learn.model.dto.SourceDto;
import com.itermit.learn.model.dto.UserDto;
import com.itermit.learn.model.entity.QuestionSet;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StartSessionRequest {

    private UserDto user;
    private CategoryDto category;
    private SourceDto source;
    private Integer totalItems;
    private EMode mode;
    private QuestionSet questionSet;
}
