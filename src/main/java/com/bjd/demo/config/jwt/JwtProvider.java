package com.bjd.demo.config.jwt;

import com.bjd.demo.service.accesstoken.AccessTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AccessTokenService accessTokenService;

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return accessTokenService.isExistToken(claims.getBody().getSubject(),
                    Long.valueOf(claims.getBody().getId()));
        } catch (JwtException | IllegalArgumentException e) {
            log.error("invalid token ", e);
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}