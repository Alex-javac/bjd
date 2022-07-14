package com.bjd.demo.mapper;

import com.bjd.demo.config.CustomUserDetails;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserDetailsMapper {
    public static final UserDetailsMapper INSTANCE = Mappers.getMapper(UserDetailsMapper.class);

    public abstract CustomUserDetails getUserDetailsFromUserEntity(UserEntity userEntity);

    public abstract UserDto getUserDtoFromUserDetails(CustomUserDetails userDetails);

}
