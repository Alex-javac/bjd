package com.bjd.demo.repository;

import com.bjd.demo.entity.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, Long> {
}
