package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class AdsDto {
    private Integer pk;
    private Integer author;
    private String image;
    private Integer price;
    private String title;
}