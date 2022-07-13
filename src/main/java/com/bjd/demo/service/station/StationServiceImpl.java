package com.bjd.demo.service.station;

import com.bjd.demo.mapper.StationMapper;
import com.bjd.demo.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StationServiceImpl implements StationService{

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

}
