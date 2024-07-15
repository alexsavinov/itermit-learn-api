package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.dto.UserDto;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateArticleRequest {

    private String title;
    private String logo;
    private String description;
    private String content;
    private Boolean visible;
    private Instant publishDate;
    private UserDto author;
}
