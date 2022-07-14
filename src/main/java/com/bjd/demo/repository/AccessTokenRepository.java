package com.bjd.demo.repository;

import com.bjd.demo.entity.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, Long> {

    Optional<AccessTokenEntity> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    Optional<AccessTokenEntity> findByRefreshToken(String token);

    Boolean existsByUserEmailAndUserIdAndIsActiveTrue(String userEmail, Long userId);

    Optional<AccessTokenEntity> findByUserIdAndRefreshToken(Long userId, String refreshToken);
}
