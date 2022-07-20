package com.bjd.demo.service.station;

import com.bjd.demo.dto.station.StationDto;

public interface StationService {
    StationDto findByName(String name);
}
