package com.bjd.demo.service.user;

import com.bjd.demo.dto.accesstoken.AccessTokenDto;
import com.bjd.demo.dto.accesstoken.AccessTokenResponseDto;
import com.bjd.demo.dto.login.LoginForm;
import com.bjd.demo.dto.user.UserDto;
import com.bjd.demo.dto.user.UserSignInResponseDto;
import com.bjd.demo.dto.user.UserSignupResponseDto;
import com.bjd.demo.entity.RoleEntity;
import com.bjd.demo.entity.UserEntity;
import com.bjd.demo.exceptions.SignInException;
import com.bjd.demo.mapper.AccessTokenMapper;
import com.bjd.demo.mapper.UserMapper;
import com.bjd.demo.repository.RoleRepository;
import com.bjd.demo.repository.UserRepository;
import com.bjd.demo.service.accesstoken.AccessTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;
    private final AccessTokenMapper accessTokenMapper;
    private final RoleRepository roleRepository;
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    @Override
    @Transactional
    public UserSignupResponseDto saveUser(UserDto userDto) {
        if (nonNull(userDto)) {
            try {
                if (preCheckUser(userDto)) {
                    log.info("User with this email already created");
                    throw new RuntimeException("User with this email=" + userDto.getEmail() + "already exists");
                }
                UserSignupResponseDto response = new UserSignupResponseDto();
                UserEntity userEntity = userMapper.mapUserDtoToEntity(userDto);
                userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                Set<RoleEntity> roles = new HashSet<>();
                roles.add(roleRepository.findByName(USER));
                if (userDto.getEmail().endsWith("@bjd.com")) {
                    roles.add(roleRepository.findByName(ADMIN));
                }
                userEntity.setRoles(roles);
                UserEntity savedUserEntity = userRepository.save(userEntity);
                log.info("User has been saved in DataBases with id:" + savedUserEntity.getId());
                response.setAccessToken(createToken(savedUserEntity.getEmail()));
                response.setEmail(savedUserEntity.getEmail());
                return response;
            } catch (Exception ex) {
                log.error("UserServiceImpl.saveUser() ", ex);
                throw new RuntimeException("error during user creation");
            }
        }
        throw new IllegalArgumentException("UserServiceImpl.saveUser() userDto can`t be null");
    }

    @Override
    @Transactional
    public UserSignInResponseDto signIn(LoginForm loginForm) {
        UserEntity user = findByEmail(loginForm.getEmail());
        if (nonNull(user)) {
            if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                UserSignInResponseDto responseDto = new UserSignInResponseDto();
                accessTokenService.deleteByUserId(user.getId());
                responseDto.setAccessToken(createToken(user.getEmail()));
                responseDto.setEmail(user.getEmail());
                return responseDto;
            }
        }
        throw new SignInException();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (nonNull(userId)) {
            userRepository.delete(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")));
        } else {
            throw new IllegalArgumentException("UserServiceImpl.deleteUser() userId can`t be null");
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.mapUserEntityToDto(userRepository.findByEmail(email).orElse(null));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> entityList = userRepository.findAll();
        return userMapper.mapUserEntityListToDtoList(entityList);
    }

    @Override
    @Transactional
    public UserDto update(Long currentUserId, UserDto userDto) {
        if (nonNull(userDto) && nonNull(currentUserId)) {
            UserEntity userEntity = userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("User not found"));
            if(nonNull(userDto.getPassword())&&!userDto.getPassword().isEmpty()){
                userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            if(nonNull(userDto.getEmail())&&!userDto.getEmail().isEmpty()){
                userEntity.setEmail(userDto.getEmail());
            }
            if(nonNull(userDto.getFirstName())&&!userDto.getFirstName().isEmpty()){
                userEntity.setFirstName(userDto.getFirstName());
            }
            if(nonNull(userDto.getLastName())&&!userDto.getLastName().isEmpty()){
                userEntity.setLastName(userDto.getLastName());
            }
            if(nonNull(userDto.getPhoneNumber())&&!userDto.getPhoneNumber().isEmpty()){
                userEntity.setPhoneNumber(userDto.getPhoneNumber());
            }
            UserEntity savedUser = userRepository.save(userEntity);
            return userMapper.mapUserEntityToDto(savedUser);
        }
        throw new IllegalArgumentException("UserServiceImpl.update() user can`t be null");
    }

    private boolean preCheckUser(UserDto userDto) {
        String email = userDto.getEmail();
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        return optionalUserEntity.isPresent();
    }

    private AccessTokenResponseDto createToken(String email) {
        AccessTokenDto token = accessTokenService.createAccessToken(email);
        AccessTokenResponseDto accessTokenResponseDto = accessTokenMapper.mapAccessTokenDtoToResponse(token);
        accessTokenResponseDto.setExpiresIn(
                token.getAccessExpirationDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        accessTokenResponseDto.setRefreshExpiresIn(
                token.getRefreshExpirationDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return accessTokenResponseDto;
    }
}
