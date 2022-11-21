package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegReqDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final UserService userService;
    private final PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserDetailsManager manager, UserService userService) {
        this.manager = manager;
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegReqDto regReqDto, RoleDto roleDto) {
        logger.info("Method to register a new User in the AuthService was invoked");
        if (userService.checkUserExists(regReqDto.getUsername()).isPresent()) {
            return false;
        }
        String encryptedPassword = encoder.encode(regReqDto.getPassword());
        regReqDto.setPassword(encryptedPassword);
        logger.info("Password has been successfully encrypted");
        regReqDto.setRole(roleDto);
        userService.createUser(regReqDto);
        logger.info("New user has been successfully created");
        return true;
//        if (manager.userExists(regReqDto.getUsername())) {
//            return false;
//        }
//        manager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .password(regReqDto.getPassword())
//                        .username(regReqDto.getUsername())
//                        .roles(roleDto.name())
//                        .build()
//        );
//        return true;
    }
}
