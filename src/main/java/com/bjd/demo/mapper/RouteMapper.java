package com.bjd.demo.mapper;

import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.entity.RouteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StationMapper.class, TrainMapper.class})
public interface RouteMapper {
    RouteDto mapRouteEntityToDto(RouteEntity routeEntity);


    RouteEntity mapRouteDtoToEntity(RouteDto routeDto);
    List<RouteEntity> mapRouteDtoListToEntityList(List<RouteDto> routeDtoList);
    List<RouteDto> mapRouteEntityListToDtoList(List<RouteEntity> routeEntityList);
}
