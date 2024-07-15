package com.itermit.learn.model.dto.request;

import com.itermit.learn.model.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    private Long id;
    private String username;
    private Set<String> role;
    private Profile profile;
}