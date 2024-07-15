package com.itermit.learn.model.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSourceRequest {

    private String name;
    private String url;
}
