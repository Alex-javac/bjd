package com.bjd.demo.repository;

import com.bjd.demo.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    Optional<StationEntity> findByName (String name);
}
