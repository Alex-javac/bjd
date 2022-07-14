package com.bjd.demo.dto.user;

import com.bjd.demo.dto.accesstoken.AccessTokenResponseDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractUserResponseDto {
    private String email;
    @JsonUnwrapped
    private AccessTokenResponseDto accessToken;
}
