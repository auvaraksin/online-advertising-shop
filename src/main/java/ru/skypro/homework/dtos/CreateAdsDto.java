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
    /* Changes've been made according to the openapi_v4.yaml */
    //    private String image;
    //    private Integer pk;
}
