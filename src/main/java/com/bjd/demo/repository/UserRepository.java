package com.bjd.demo.repository;

import com.bjd.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u left join fetch u.roles where u.email = ?1")
    Optional<UserEntity> getUserByEmailWithRoles(String email);

    Optional<UserEntity> findByEmail(String email);
}
