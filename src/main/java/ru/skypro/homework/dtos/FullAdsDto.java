package ru.skypro.homework.dtos;

import lombok.Data;

@Data
public class FullAdsDto {
    private Integer pk;
    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;
    private String image;
    private Integer price;
    private String title;
    private String description;
}