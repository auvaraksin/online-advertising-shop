package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegReqDto;
import ru.skypro.homework.dto.RoleDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserDetailsService userDetailsService,
                           UserService userService,
                           PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String userName, String password) {
        if (userService.findUserByEmail(userName).isEmpty()) {
            logger.info(userName + " does not exist");
            return false;
        }
        logger.info("Method to authorise user was invoked");
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        System.out.println("Password validation status:" + encryptedPasswordWithoutEncryptionType.equals(password));
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegReqDto regReqDto, RoleDto roleDto) {
        logger.info("Method to register a new User in the AuthService was invoked");
        if (userService.findUserByEmail(regReqDto.getUsername()).isPresent()) {
            return false;
        }
        String encryptedPassword = encoder.encode(regReqDto.getPassword());
        regReqDto.setPassword(encryptedPassword);
        logger.info("Password has been successfully encrypted");
        regReqDto.setRole(roleDto);
        userService.createUser(regReqDto);
        logger.info("New user has been successfully created");
        return true;
    }
}
