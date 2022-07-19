package com.bjd.demo.mapper;

import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapUserEntityToDto(UserEntity userEntity);


    UserEntity mapUserDtoToEntity(UserDto userDto);
    List<UserDto> mapUserEntityListToDtoList(List<UserEntity> userEntityList);
}
