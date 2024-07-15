package com.itermit.learn.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itermit.learn.model.entity.Profile;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {

    private Long id;
    private String username;
    private String createdDate;
    private String lastUpdateDate;
    private List<String> roles;
    private Profile profile;
}
