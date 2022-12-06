package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    private Integer pk;
    private Integer author;
    private LocalDateTime createdAt;
    private String text;
}