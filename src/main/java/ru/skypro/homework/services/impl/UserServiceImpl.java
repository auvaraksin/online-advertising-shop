package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void updateUser(RegReqDto regReqDto, RoleDto roleDto) {
        regReqDto.setRole(roleDto);
        User userMapped = userMapper.regReqDtoToUser(regReqDto);
        User user = findUserByEmail(regReqDto.getUsername()).orElseThrow();
        user.setFirstName(userMapped.getFirstName());
        user.setLastName(userMapped.getLastName());
        user.setPhone(userMapped.getPhone());
        user.setRole(userMapped.getRole());
        logger.info("Method to update User " + user + " in the DB in table 'users' was invoked");
        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        logger.info("Method to find record with user data by = " + email + " in the DB in table 'users' was invoked");
        return userRepository.findUserByEmailIgnoreCase(email);
    }

    public Optional<User> findUserById(Integer id) {
        logger.info("Method to find record with user data by ID = " + id + " in the DB in table 'users' was invoked");
        return userRepository.findUserById(id);
    }

    public ResponseEntity<ResponseWrapperUserDto> getUsers() {
        logger.info("Method to find all users in the DB in table 'users' was invoked");
        List<User> userList = userRepository.findAll();
        ResponseWrapperUserDto responseWrapperUserDto = new ResponseWrapperUserDto();
        List<UserDto> userDtoList = userMapper.usersToUsersDto(userList);
        responseWrapperUserDto.setCount(userDtoList.size());
        responseWrapperUserDto.setResults(userDtoList);
        return ResponseEntity.ok(responseWrapperUserDto);
    }

    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        logger.info("Method to update user by its ID in the DB in table 'users' was invoked");
        if (userDto.getId() != null
                && userDto.getFirstName() != null
                && userDto.getLastName() != null
                && userDto.getEmail() != null
                && userDto.getPhone() != null
                && userRepository.findUserById(userDto.getId()).isPresent()) {
            User user = userRepository.findUserById(userDto.getId()).orElseThrow();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            userRepository.save(user);
            logger.info("User ID = " + user.getId() + " was successfully updated");
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<NewPasswordDto> setPassword(NewPasswordDto newPasswordDto, Principal principal) {
        if (newPasswordDto.getCurrentPassword() != null
                && newPasswordDto.getNewPassword() != null
                && principal.getName() != null) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            logger.info("Method to validate password was invoked");
            if (encoder.matches(newPasswordDto.getCurrentPassword(),
                    userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow().getPassword())) {
                logger.info("User password was successfully validated");
                User user = userRepository.findUserByEmailIgnoreCase(principal.getName()).orElseThrow();
                user.setPassword(encoder.encode(newPasswordDto.getNewPassword()));;
                userRepository.save(user);
                logger.info("User password was successfully updated");
                return ResponseEntity.ok(newPasswordDto);
            }
        }
        logger.info("User password checking validation was failed");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<UserDto> getUser(Integer id) {
        logger.info("Method to find User by its ID was invoked");
        if (userRepository.findUserById(id).isPresent()) {
            User user = userRepository.findUserById(id).orElseThrow();
            UserDto userDto = userMapper.userToUserDto(user);
            logger.info("User data by ID = " + id + " in the DB in table 'users' was successfully found");
            return ResponseEntity.ok(userDto);
        }
        logger.info("User data by ID = " + id + " was not found");
        return ResponseEntity.notFound().build();
    }
}
