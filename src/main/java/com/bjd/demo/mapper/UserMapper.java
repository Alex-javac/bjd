package com.bjd.demo.mapper;

import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapUserEntityToDto(UserEntity userEntity);

    UserEntity mapUserDtoToEntity(UserDto userDto);
}
