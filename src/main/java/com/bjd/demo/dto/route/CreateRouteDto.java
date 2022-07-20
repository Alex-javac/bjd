package com.bjd.demo.dto.route;

import com.bjd.demo.entity.TrainType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateRouteDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalTime;
    private Double price;
    private String departureStation;
    private String arrivalStation;
    private String trainNumber;
    private TrainType type;
}
