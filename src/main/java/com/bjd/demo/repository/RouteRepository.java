package com.bjd.demo.repository;

import com.bjd.demo.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    List<RouteEntity> findAllByDepartureStationNameAndArrivalStationNameAndDepartureTime(
            String departureStationName,
            String arrivalStationName,
            LocalDate departureTime);
    List<RouteEntity> findAllByDepartureStationNameAndArrivalStationName(
            String departureStationName,
            String arrivalStationName);

}
