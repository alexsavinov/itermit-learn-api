package com.itermit.learn.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {

    private String refreshToken ;
}
