package com.bjd.demo.service.accesstoken;

import static com.bjd.demo.util.SecurityUtilService.generateToken;
import static com.bjd.demo.util.UtilConst.REFRESH_TOKEN_EXPIRATION_IN_DAYS;
import static com.bjd.demo.util.UtilConst.TOKEN_EXPIRATION_IN_DAYS;
import static java.util.Objects.nonNull;

import com.bjd.demo.dto.accesstoken.AccessTokenDto;
import com.bjd.demo.entity.AccessTokenEntity;
import com.bjd.demo.entity.UserEntity;
import com.bjd.demo.mapper.AccessTokenMapper;
import com.bjd.demo.repository.AccessTokenRepository;
import com.bjd.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final AccessTokenMapper accessTokenMapper;
    private final UserRepository userRepository;

    @Override
    public AccessTokenDto findByUserId(Long userId) {
        if (nonNull(userId)) {
            AccessTokenEntity refreshToken =
                    accessTokenRepository.findByUserId(userId).<IllegalArgumentException>orElseThrow(() -> {
                        throw new IllegalArgumentException(
                                "AccessTokenServiceImpl.findByUserId() AccessToken not found");
                    });
            return accessTokenMapper.fromAccessTokenEntity(refreshToken);
        }
        throw new IllegalArgumentException("AccessTokenServiceImpl.findByUserId() userId can`t be null");
    }

    @Override
    @Transactional
    public AccessTokenDto createAccessToken(String email) {
        if (nonNull(email)) {
            UserEntity userEntity = userRepository.findByEmail(email).<IllegalArgumentException>orElseThrow(() -> {
                throw new IllegalArgumentException("RefreshTokenServiceImpl.createRefreshToken() User not found");
            });
            AccessTokenEntity accessToken = AccessTokenEntity.builder()
                    .accessToken(generateToken(userEntity))
                    .refreshToken(UUID.randomUUID().toString())
                    .userEmail(email)
                    .userId(userEntity.getId())
                    .isActive(true)
                    .refreshExpirationDate(LocalDate.now().plusDays(REFRESH_TOKEN_EXPIRATION_IN_DAYS))
                    .accessExpirationDate(LocalDate.now().plusDays(TOKEN_EXPIRATION_IN_DAYS))
                    .build();
            return accessTokenMapper.fromAccessTokenEntity(accessTokenRepository.save(accessToken));
        }
        throw new IllegalArgumentException("AccessTokenServiceImpl.createAccessToken() email can`t be null");
    }

    @Override
    @Transactional
    public void deactivateToken(String refreshToken, Long userId) {
        AccessTokenEntity accessTokenEntity =
                accessTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken).orElse(null);
        if (!nonNull(accessTokenEntity)) {
            throw new IllegalArgumentException("AccessTokenServiceImpl.deactivateToken() AccessToken not found");
        }
        accessTokenEntity.setIsActive(false);
        accessTokenRepository.save(accessTokenEntity);
    }

    @Override
    public Boolean isExistToken(String userEmail, Long userId) {
        return accessTokenRepository.existsByUserEmailAndUserIdAndIsActiveTrue(userEmail, userId);
    }

    @Override
    public AccessTokenDto findByRefreshToken(String token) {
        if (nonNull(token)) {
            AccessTokenEntity refreshToken =
                    accessTokenRepository.findByRefreshToken(token).<IllegalArgumentException>orElseThrow(() -> {
                        throw new IllegalArgumentException(
                                "AccessTokenServiceImpl.findByToken() AccessToken not found");
                    });
            return accessTokenMapper.fromAccessTokenEntity(refreshToken);
        }
        throw new IllegalArgumentException("AccessTokenServiceImpl.findByToken() token can`t be null");
    }

    public void verifyExpiration(AccessTokenDto token) {
        if (token.getRefreshExpirationDate().isBefore(LocalDate.now()) || !token.getIsActive()) {
            throw new IllegalArgumentException("Expired token. Please issue a new request");
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        accessTokenRepository.deleteByUserId(userId);
        accessTokenRepository.flush();
    }
}
