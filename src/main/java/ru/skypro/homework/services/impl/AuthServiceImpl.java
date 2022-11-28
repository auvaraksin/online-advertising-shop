package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.RoleDto;
import ru.skypro.homework.services.AuthService;
import ru.skypro.homework.services.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder encoder;

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(JdbcUserDetailsManager jdbcUserDetailsManager, UserService userService) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.encoder = new BCryptPasswordEncoder();
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!jdbcUserDetailsManager.userExists(userName)) {
            logger.info(userName + " does not exist");
            return false;
        }
        logger.info("Method to authorise user (username = " + userName + ") was invoked");
        UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        logger.info("Password validation status:" + encoder.matches(password, encryptedPassword));
        return encoder.matches(password, encryptedPassword);
    }

    @Override
    public boolean register(RegReqDto regReqDto, RoleDto roleDto) {
        logger.info("Method to register a new User (username = " + regReqDto.getUsername() + ") was invoked");
        if (jdbcUserDetailsManager.userExists(regReqDto.getUsername())) {
            return false;
        }
        String encryptedPassword = encoder.encode(regReqDto.getPassword());
        regReqDto.setPassword(encryptedPassword);
        logger.info("Password (username = " + regReqDto.getUsername() + ") has been successfully encrypted");
        regReqDto.setRole(roleDto);
        jdbcUserDetailsManager.createUser(User.withUsername(regReqDto.getUsername())
                .username(regReqDto.getUsername())
                .password(regReqDto.getPassword())
                .roles(String.valueOf(regReqDto.getRole()))
                .build());
        userService.updateUser(regReqDto, roleDto);
        logger.info("New user (username = " + regReqDto.getUsername() + ") has been successfully created");
        return true;
    }
}
