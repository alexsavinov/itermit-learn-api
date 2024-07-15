package com.itermit.learn.model.dto.request;

import lombok.*;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishSessionRequest {

    private Long id;
    private Instant finishedDate;
}
