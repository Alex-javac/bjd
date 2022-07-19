package com.bjd.demo.dto.user;

import com.bjd.demo.dto.ticket.TicketDto;
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

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }
}
