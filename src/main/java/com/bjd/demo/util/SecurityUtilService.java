package com.bjd.demo.util;

import com.bjd.demo.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.bjd.demo.util.UtilConst.TOKEN_EXPIRATION_IN_DAYS;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtilService {

    private static final String JWT_SECRET = "gather";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE_NUMBER = "phoneNumber";

    public static String generateToken(UserEntity user) {
        Claims claims = getUserClaims(user);
        Date now = Date.from(LocalDate.now().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date date = Date.from(LocalDate.now()
                .plusDays(TOKEN_EXPIRATION_IN_DAYS)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public static Claims getUserClaims(UserEntity user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.setId(user.getId().toString());
        claims.put(FIRST_NAME, user.getFirstName());
        claims.put(LAST_NAME, user.getLastName());
        claims.put(PHONE_NUMBER, user.getPhoneNumber());
        return claims;
    }
}