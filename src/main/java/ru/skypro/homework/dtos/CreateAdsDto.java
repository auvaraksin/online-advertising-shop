package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CreateAdsDto {
    @NotNull
    private String description;
    @NotNull
    @Positive
    private Integer price;
    @NotBlank
    private String title;
}
