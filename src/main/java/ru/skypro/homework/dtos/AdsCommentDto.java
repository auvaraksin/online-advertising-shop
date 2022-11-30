package ru.skypro.homework.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    @NotNull
    @Positive
    private Integer pk;
    @NotNull
    @Positive
    private Integer author;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private String text;
}
