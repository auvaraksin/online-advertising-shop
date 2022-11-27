package ru.skypro.homework.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    private Integer pk;
    private Integer author;
    private LocalDateTime createdAt;
    private String text;
}
