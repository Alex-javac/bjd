package com.bjd.demo.mapper;

import com.bjd.demo.dto.ticket.TicketDto;
import com.bjd.demo.entity.TicketEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RouteMapper.class})
public interface TicketMapper {
    TicketDto mapTicketEntityToDto(TicketEntity ticketEntity);

    TicketEntity mapTicketDtoToEntity(TicketDto ticketDto);

    List<TicketDto> mapTicketEntityListToDtoList(List<TicketEntity> ticketEntity);
}
