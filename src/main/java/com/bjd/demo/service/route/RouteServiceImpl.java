package com.bjd.demo.service.route;

import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.entity.RouteEntity;
import com.bjd.demo.mapper.RouteMapper;
import com.bjd.demo.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    @Override
    public List<RouteDto> find(FindRouteDto findRouteDto) {
        if (nonNull(findRouteDto)) {
            List<RouteEntity> routeEntityList;
            if(nonNull(findRouteDto.getDepartureTime())) {
                 routeEntityList = routeRepository.findAllByDepartureStationNameAndArrivalStationNameAndDepartureTime(
                        findRouteDto.getNameDepartureStation(),
                        findRouteDto.getNameArrivalStation(),
                        findRouteDto.getDepartureTime()
                );
            }else {
                routeEntityList = routeRepository.findAllByDepartureStationNameAndArrivalStationName(
                        findRouteDto.getNameDepartureStation(),
                        findRouteDto.getNameArrivalStation()
                );
            }
            return routeMapper.mapRouteEntityListToDtoList(routeEntityList);
        }
        throw new IllegalArgumentException("RouteServiceImpl.find() findRouteDto can`t be null");
    }
}
