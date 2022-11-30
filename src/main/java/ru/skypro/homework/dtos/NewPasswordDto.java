package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewPasswordDto {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
