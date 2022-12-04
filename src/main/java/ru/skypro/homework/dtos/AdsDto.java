package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AdsDto {
    @NotNull
    @Positive
    private Integer pk;
    @NotNull
    @Positive
    private Integer author;
    @NotNull
    private String image;
    @NotNull
    @Positive
    private Integer price;
    @NotNull
    private String title;
}
