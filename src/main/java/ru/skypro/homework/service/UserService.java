package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegReqDto;
import ru.skypro.homework.entity.User;

import java.util.Optional;

public interface UserService {
    void createUser(RegReqDto regReqDto);

    Optional<User> findUserByEmail(String email);
}
