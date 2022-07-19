package com.bjd.demo.service.user;

import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.dto.user.UserSignupResponseDto;
import com.bjd.demo.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserSignupResponseDto saveUser(UserDto userDto);

    UserSignInResponseDto signIn(LoginForm loginForm);

    void deleteUser(Long userId);

    UserDto getUserByEmail(String email);

    UserEntity findByEmail(String email);

    List<UserDto> findAll();

    UserDto update(Long currentUserId, UserDto userDto);
}
