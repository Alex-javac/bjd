package com.bjd.demo.service.accesstoken;

import com.bjd.demo.dto.accesstoken.AccessTokenDto;

public interface AccessTokenService {
    AccessTokenDto findByUserId(Long userId);

    AccessTokenDto createAccessToken(String email);

    AccessTokenDto findByRefreshToken(String token);

    void deleteByUserId(Long userId);

    void verifyExpiration(AccessTokenDto token);

    void deactivateToken(String refreshToken, Long userId);

    Boolean isExistToken(String userEmail, Long userId);
}
