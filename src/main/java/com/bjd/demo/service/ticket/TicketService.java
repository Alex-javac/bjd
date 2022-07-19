package com.bjd.demo.service.ticket;

import com.bjd.demo.dto.ticket.TicketDto;

import java.util.List;

public interface TicketService {
    TicketDto create(Long routeId, Long userId);

    List<TicketDto> findAllByUserId(Long userId);

    void delete(long ticketId);
}
