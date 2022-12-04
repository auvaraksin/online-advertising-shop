package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegReqDto;
import ru.skypro.homework.dto.RoleDto;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReqDto regReqDto, RoleDto roleDto);
}
