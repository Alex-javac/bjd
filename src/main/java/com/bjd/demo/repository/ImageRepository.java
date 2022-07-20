package com.bjd.demo.repository;

import com.bjd.demo.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByUserId(Long userId);
    void deleteByUserId (Long userId);
}
