package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAdsDto {
    private String description;
    private Integer price;
    private String title;
    /* Changes've been made according to the openapi_v4.yaml */
    //    private String image;
    //    private Integer pk;
}
