package com.bjd.demo.mapper;

import com.bjd.demo.dto.accesstoken.AccessTokenDto;
import com.bjd.demo.dto.accesstoken.AccessTokenResponseDto;
import com.bjd.demo.entity.AccessTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccessTokenMapper {

    AccessTokenEntity fromAccessTokenDto(AccessTokenDto accessTokenDto);

    AccessTokenDto fromAccessTokenEntity(AccessTokenEntity accessTokenEntity);

    AccessTokenResponseDto mapAccessTokenDtoToResponse(AccessTokenDto accessTokenDto);
}
