package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.ResponseWrapperUserDto;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.services.UserService;

import java.security.Principal;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users Rest Controller", description = "CRUD операции с пользователями")
public class UserController {

    private final UserService userService;

    @Operation(summary = "getUsers", description = "Получить пользователей", tags = {"Пользователи"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseWrapperUserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperUserDto> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "updateUser", description = "Обновить пользователя", tags = {"Пользователи",})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @Operation(summary = "setPassword", description = "Установить пароль", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = NewPasswordDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto, Principal principal) {
        return userService.setPassword(newPasswordDto, principal);
    }

    @Operation(summary = "getUser", description = "Получить пользователя по id", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }
}
