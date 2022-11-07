package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
}
