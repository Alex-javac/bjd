package com.bjd.demo.repository;

import com.bjd.demo.entity.TrainEntity;
import com.bjd.demo.entity.TrainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
    Optional<TrainEntity> findByNumberAndType(String number, TrainType type);
}
