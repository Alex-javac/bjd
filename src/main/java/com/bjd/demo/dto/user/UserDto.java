package com.bjd.demo.dto.user;

import com.bjd.demo.dto.ticket.TicketDto;
import com.bjd.demo.entity.TicketEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;
    private List<TicketDto> tickets;
}
