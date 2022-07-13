package com.bjd.demo.service.user;

import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.entity.UserEntity;
import com.bjd.demo.mapper.UserMapper;
import com.bjd.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto saveUser(UserDto userDto) {
        if (nonNull(userDto)) {
            UserEntity user = userMapper.mapUserDtoToEntity(userDto);
            UserEntity userEntity = userRepository.save(user);
            return userMapper.mapUserEntityToDto(userEntity);
        }
        throw new IllegalArgumentException("UserServiceImpl.saveUser() userDto can`t be null");
    }
}
