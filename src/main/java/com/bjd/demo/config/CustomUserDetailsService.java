package com.bjd.demo.config;

import com.bjd.demo.entity.UserEntity;
import com.bjd.demo.mapper.UserDetailsMapper;
import com.bjd.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getUserByEmailWithRoles(email).orElseThrow(() -> {
            log.error("CustomUserDetailsService.loadUserByUsername() user not found");
            return new UsernameNotFoundException("User not found");
        });
        CustomUserDetails userDetails = UserDetailsMapper.INSTANCE.getUserDetailsFromUserEntity(userEntity);
        List<GrantedAuthority> authorities = userEntity.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());
        userDetails.setAuthorities(authorities);
        return userDetails;
    }
}