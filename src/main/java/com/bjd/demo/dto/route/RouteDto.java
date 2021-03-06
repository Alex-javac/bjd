package com.bjd.demo.dto.route;

import com.bjd.demo.dto.station.StationDto;
import com.bjd.demo.dto.train.TrainDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteDto {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalTime;
    private Double price;
    private LocalDateTime createdAt;
    private StationDto departureStation;
    private StationDto arrivalStation;
    private TrainDto train;
}
