package com.bjd.demo.dto.route;

import com.bjd.demo.dto.station.StationDto;
import com.bjd.demo.dto.train.TrainDto;
import com.bjd.demo.entity.StationEntity;
import com.bjd.demo.entity.TrainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {
    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private LocalDateTime createdAt;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private TrainDto train;
}
