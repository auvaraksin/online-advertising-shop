package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class AdsDto {
    private Integer pk;
    private Integer author;
    private String image;
    private Integer price;
    private String title;
}