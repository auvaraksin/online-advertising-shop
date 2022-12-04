package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class FullAdsDto {
    @NotNull
    @Positive
    private Integer pk;
    @NotNull
    private String authorFirstName;
    @NotNull
    private String authorLastName;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String image;
    @NotNull
    @Positive
    private Integer price;
    @NotNull
    private String title;
    @NotNull
    private String description;
}
