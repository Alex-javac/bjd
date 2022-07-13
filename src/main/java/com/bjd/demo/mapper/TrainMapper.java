package com.bjd.demo.mapper;

import com.bjd.demo.dto.train.TrainDto;
import com.bjd.demo.entity.TrainEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RouteMapper.class})
public interface TrainMapper {
    TrainDto mapTrainEntityToDto(TrainEntity trainEntity);
    TrainEntity mapTrainDtoToEntity(TrainDto trainDto);
}
