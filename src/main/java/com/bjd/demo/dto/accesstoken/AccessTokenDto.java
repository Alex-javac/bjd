package com.bjd.demo.dto.accesstoken;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDto {

    private Long id;
    private String accessToken;
    private String refreshToken;
    private String userEmail;
    private Boolean isActive;
    private LocalDate refreshExpirationDate;
    private LocalDate accessExpirationDate;
    private Long userId;
}
