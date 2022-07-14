package com.bjd.demo.service.user;

import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.dto.user.UserSignupResponseDto;
import com.bjd.demo.entity.UserEntity;

public interface UserService {
    UserSignupResponseDto saveUser(UserDto userDto);
    UserSignInResponseDto signIn(LoginForm loginForm);

    void deleteUser(UserDto userDto);

    void signOut(String refreshToken, Long userId);

    UserDto getUserByEmail(String email);

    UserEntity findByEmail(String email);
}
