package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegReqDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

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

    public void createUser(RegReqDto regReqDto) {
        User user = userMapper.regReqDtoToUser(regReqDto);
        logger.info("ReqReqDto has been successfully mapped to User entity");
        userRepository.save(user);
        logger.info("Method to create a new record " + user + " in the DB in table 'User' was invoked");
    }

    public Optional<User> checkUserExists(String email) {
        logger.info("Method to check if the record = {" + email + "} in the DB in table 'User' does exist was invoked");
        return userRepository.findUserByEmailIgnoreCase(email);
    }
}
