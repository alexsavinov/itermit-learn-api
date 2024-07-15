package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.QuestionSetDto;
import com.itermit.learn.model.dto.UserDto;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSessionRequest {

    private UserDto user;
    private QuestionSetDto questionSet;
    private Instant finishedDate;
}
