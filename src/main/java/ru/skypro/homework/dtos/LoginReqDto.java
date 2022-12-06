package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginReqDto {
    private String password;
    private String username;

}