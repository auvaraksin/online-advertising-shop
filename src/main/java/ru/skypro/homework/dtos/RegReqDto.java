package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegReqDto {
    private String password;
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private RoleDto role;
}