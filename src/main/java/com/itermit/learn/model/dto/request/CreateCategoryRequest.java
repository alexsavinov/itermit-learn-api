package com.itermit.learn.model.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryRequest {

    private String name;
}
