package ru.skypro.homework.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperUserDto {
    private Integer count;
    private List<UserDto> results;

}
