package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RegReqDto {
    private String password;
    private String username;
    private RoleDto role;
    /* Changes've been made according to the openapi_v4.yaml */
//    private String firstName;
//    private String lastName;
//    private String phone;
}
