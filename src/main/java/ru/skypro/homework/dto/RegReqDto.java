package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RegReqDto {
    private String password;
    private RoleDto role;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
}
