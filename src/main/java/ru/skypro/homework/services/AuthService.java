package ru.skypro.homework.services;

import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.RoleDto;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReqDto regReqDto, RoleDto roleDto);
}
