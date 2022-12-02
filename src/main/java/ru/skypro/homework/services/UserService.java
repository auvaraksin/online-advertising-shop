package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.entities.User;

import java.security.Principal;
import java.util.Optional;

public interface UserService {
    void updateUser(RegReqDto regReqDto, RoleDto roleDto);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Integer id);

    ResponseEntity<ResponseWrapperUserDto> getUsers();

    ResponseEntity<UserDto> updateUser(UserDto userDto);

    ResponseEntity<NewPasswordDto> setPassword(NewPasswordDto newPasswordDto, Principal principal);

    ResponseEntity<UserDto> getUser(Integer id);
}
