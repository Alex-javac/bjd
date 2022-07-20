package com.bjd.demo.service.station;

import com.bjd.demo.dto.station.StationDto;
import com.bjd.demo.entity.StationEntity;
import com.bjd.demo.mapper.StationMapper;
import com.bjd.demo.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Override
    public StationDto findByName(String name) {
        if (nonNull(name)) {
            StationEntity stationEntity = stationRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Station not found"));
            return stationMapper.mapStationEntityToDto(stationEntity);
        }
        throw new IllegalArgumentException("StationServiceImpl.findByName() name can`t be null");
    }
}
