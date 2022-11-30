package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.RoleDto;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.UserService;

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
        logger.info("Method to update User " + user + " in the DB in table 'User' was invoked");
        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        logger.info("Method to find record with user data by = {" + email + "} in the DB in table 'User' was invoked");
        return userRepository.findUserByEmailIgnoreCase(email);
    }

    public Optional<User> findUserById(Integer id) {
        logger.info("Method to find record with user data by ID = {" + id + "} in the DB in table 'User' was invoked");
        return userRepository.findUserById(id);
    }

}
