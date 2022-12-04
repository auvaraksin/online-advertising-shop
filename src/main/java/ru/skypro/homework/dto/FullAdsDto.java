package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class FullAdsDto {
    private String image;
    private String authorFirstName;
    private String authorLastName;
    private String phone;
    private Integer price;
    private String description;
    private Integer pk;
    private String title;
    private String email;
}
