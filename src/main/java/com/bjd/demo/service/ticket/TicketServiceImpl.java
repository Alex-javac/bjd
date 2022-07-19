package com.bjd.demo.service.ticket;

import com.bjd.demo.dto.ticket.TicketDto;
import com.bjd.demo.entity.TicketEntity;
import com.bjd.demo.mapper.TicketMapper;
import com.bjd.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public TicketDto create(Long routeId, Long userId) {
        if(nonNull(routeId)&&nonNull(userId)){
            TicketEntity ticket = ticketRepository.save(new TicketEntity(userId, routeId));
            return ticketMapper.mapTicketEntityToDto(ticket);
        }
        throw new IllegalArgumentException("TicketServiceImpl.create() parameters can`t be null");
    }

    @Override
    public List<TicketDto> findAllByUserId(Long userId) {
        if(nonNull(userId)){
            List<TicketEntity> ticketEntityList = ticketRepository.findAllByUserId(userId);
            return ticketMapper.mapTicketEntityListToDtoList(ticketEntityList);
        }
        throw new IllegalArgumentException("TicketServiceImpl.findAllByUserId() userId can`t be null");
    }

    @Override
    @Transactional
    public void delete(long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
