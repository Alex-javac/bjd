package com.bjd.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilConst {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final Long TOKEN_EXPIRATION_IN_DAYS = 5L;
    public static final Long REFRESH_TOKEN_EXPIRATION_IN_DAYS = 10L;
}
