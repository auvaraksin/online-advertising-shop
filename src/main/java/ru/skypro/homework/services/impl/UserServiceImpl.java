package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.RegReqDto;
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

    public void registerUser(RegReqDto regReqDto) {
        User user = userMapper.regReqDtoToUser(regReqDto);
        logger.info("ReqReqDto has been successfully mapped to User entity");
        userRepository.save(user);
        logger.info("Method to create a new record " + user + " in the DB in table 'User' was invoked");
    }

    public Optional<User> findUserByEmail(String email) {
        logger.info("Method to find record with user data by = {" + email + "} in the DB in table 'User' was invoked");
        return userRepository.findUserByEmailIgnoreCase(email);
    }
}
