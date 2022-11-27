package ru.skypro.homework.services;

import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.entities.User;

import java.util.Optional;

public interface UserService {
    void registerUser(RegReqDto regReqDto);

    Optional<User> findUserByEmail(String email);
}
