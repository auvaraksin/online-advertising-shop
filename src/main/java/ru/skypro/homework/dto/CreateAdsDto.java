package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAdsDto {
    private String image;
    private String description;
    private Integer pk;
    private Integer price;
    private String title;
}
