package com.bjd.demo.dto.ticket;

import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;
    private UserEntity user;
    private RouteDto route;
}
