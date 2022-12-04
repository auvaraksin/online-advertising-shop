package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegReqDto {
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String phone;
    @NotNull
    private RoleDto role;
}
