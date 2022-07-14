package com.bjd.demo.dto.route;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindRouteDto {
    private String nameDepartureStation;
    private String nameArrivalStation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureTime;
}
