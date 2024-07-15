package com.itermit.learn.model.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryRequest {

    private Long id;
    private String name;
}
