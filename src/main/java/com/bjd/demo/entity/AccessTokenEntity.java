package com.bjd.demo.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_tokens")
public class AccessTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "refresh_expiration_date")
    private LocalDate refreshExpirationDate;
    @Column(name = "access_expiration_date")
    private LocalDate accessExpirationDate;
    @Column(name = "user_id")
    private Long userId;
}
