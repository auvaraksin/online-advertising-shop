package ru.skypro.homework.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    private LocalDateTime createAt;
    private Integer author;
    private Integer pk;
    private String text;
}
