package com.bjd.demo.mapper;

import com.bjd.demo.dto.image.ImageDto;
import com.bjd.demo.entity.ImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StationMapper.class})
public interface ImageMapper {
    ImageDto mapImageEntityToDto(ImageEntity imageEntity);

    ImageEntity mapImageDtoToEntity(ImageDto imageDto);
}
