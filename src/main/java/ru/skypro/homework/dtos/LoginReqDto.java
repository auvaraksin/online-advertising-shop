package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginReqDto {
    @NotNull
    private String password;
    @NotNull
    private String username;

}
