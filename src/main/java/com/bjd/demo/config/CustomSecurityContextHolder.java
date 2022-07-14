package com.bjd.demo.config;

import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.mapper.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomSecurityContextHolder {

    public static UserDto getCurrentUser() {
        CustomUserDetails customUserDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserDetailsMapper.INSTANCE.getUserDtoFromUserDetails(customUserDetails);
    }

    public static Long getCurrentUserId() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getId();
    }
}
