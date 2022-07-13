package com.bjd.demo.mapper;

import com.bjd.demo.dto.station.StationDto;
import com.bjd.demo.entity.StationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface StationMapper {
    StationDto mapStationEntityToDto(StationEntity stationEntity);

    StationEntity mapStationDtoToEntity(StationDto stationDto);
}
