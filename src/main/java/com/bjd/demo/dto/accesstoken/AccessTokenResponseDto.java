package com.bjd.demo.dto.accesstoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponseDto {
    @JsonProperty("bearer_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;

    @Override
    public String toString() {
        return "AccessTokenDto{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshExpiresIn=" + refreshExpiresIn +
                '}';
    }
}
