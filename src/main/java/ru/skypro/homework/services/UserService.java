package ru.skypro.homework.services;

import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.RoleDto;
import ru.skypro.homework.entities.User;

import java.util.Optional;

public interface UserService {
    void updateUser(RegReqDto regReqDto, RoleDto roleDto);
    Optional<User> findUserByEmail(String email);
}
