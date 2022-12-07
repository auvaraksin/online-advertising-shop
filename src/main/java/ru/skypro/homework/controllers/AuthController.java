package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dtos.LoginReqDto;
import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.services.AuthService;

import static ru.skypro.homework.dtos.RoleDto.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth Rest Controller", description = "CRUD операции с авторизацией")
public class AuthController {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Авторизация текущего пользователя.
     */
    @Operation(summary = "login", description = "", tags = {"Авторизация"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PostMapping("/login")
    public ResponseEntity<?> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры авторизации")
                                   @RequestBody LoginReqDto loginReqDto
    ) {
        logger.info("Авторизация пользователя: {}", loginReqDto);

        authService.login(loginReqDto.getUsername(), loginReqDto.getPassword());

        return ResponseEntity.ok().build();
    }

    /**
     * Регистраиця пользователя.
     */
    @Operation(summary = "register", description = "", tags = {"Авторизация"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PostMapping("/register")
    public ResponseEntity<?> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры регистрации")
                                      @RequestBody RegReqDto regReqDto
    ) {
        logger.info("Регистрация пользователя: {}", regReqDto);

        authService.register(regReqDto, USER);

        return ResponseEntity.ok().build();
    }
}