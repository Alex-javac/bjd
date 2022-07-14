package com.bjd.demo.dto.route;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindRouteDto {
    private String nameDepartureStation;
    private String nameArrivalStation;
    private LocalDate departureTime;
}
