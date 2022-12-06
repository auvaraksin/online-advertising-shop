package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.dtos.ResponseWrapperUserDto;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.entities.Avatar;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.services.AuthService;
import ru.skypro.homework.services.AvatarService;
import ru.skypro.homework.services.UserService;

import javax.validation.Valid;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Tag(name = "Users Rest Controller", description = "CRUD операции с пользователями")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthService authService;

    private final AvatarService avatarService;

    @Autowired
    public UserController(UserMapper userMapper,
                          AuthService authService,
                          UserService userService,
                          AvatarService avatarService) {
        this.userMapper = userMapper;
        this.authService = authService;
        this.userService = userService;
        this.avatarService = avatarService;
    }

    /**
     * Возвращает данные текущего пользователя.
     */
    @Operation(summary = "getUsers", description = "Получить пользователей", tags = {"Пользователи"})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUsers(Authentication authentication) {
        LOGGER.info("Получение данных пользователя {}", authentication.getName());
        return ResponseEntity.ok(userMapper.userToUserDto(userService.findUserByName(authentication.getName())));
    }

    /**
     * Обновляет данные текущего пользователя.
     */
    @Operation(summary = "updateUser", description = "Обновить пользователя", tags = {"Пользователи",})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(Authentication authentication,
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры пользователя")
                                              @RequestBody @Valid UserDto userDto
    ) {
        LOGGER.info("Обновление данных пользователя {}: {}", authentication.getName(), userDto);

        UserEntity user = userService.findUserByName(authentication.getName());
        userDto.setEmail(authentication.getName());
        userDto.setId(Math.toIntExact(user.getId()));

        UserEntity userUpdated = userService.updateUser(userMapper.userDtoToUser(userDto));

        return ResponseEntity.ok(userMapper.userToUserDto(userUpdated));
    }

    /**
     * Обновляет пароль.
     */
    @Operation(summary = "setPassword", description = "Установить пароль", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(Authentication authentication,
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры пароля")
                                                      @RequestBody @Valid NewPasswordDto newPasswordDto
    ) {
        LOGGER.info("Обновление пароля пользователя {}: {}", authentication.getName(), newPasswordDto);

        authService.changePassword(
                authentication,
                newPasswordDto.getCurrentPassword(),
                newPasswordDto.getNewPassword()
        );

        return ResponseEntity.ok(newPasswordDto);
    }

    /**
     * Возвращает данные пользователя по его ID.
     */
    @Operation(summary = "getUser", description = "Получить пользователя по id", tags = {"Пользователи"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(Authentication authentication,
                                           @Parameter(description = "ID пользователя")
                                           @PathVariable("id") Integer id
    ) {
        LOGGER.info("Получение данных пользователя {}", id);

        UserEntity user = userService.findUser(id);
        if (!user.getUsername().equals(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    /**
     * Обновляет изображение текущего пользователя.
     */
    @Operation(
            summary = "Обновить изображение текущего пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(
            Authentication authentication,
            @Parameter(description = "Изображение")
            @RequestParam MultipartFile image
    ) {
        LOGGER.info("Обновление изображения пользователя {}", authentication.getName());

        UserEntity user = userService.findUserByName(authentication.getName());
        avatarService.updateAvatar(user, image);

        return ResponseEntity.ok().build();
    }

    /**
     * Возвращает изображение текущего пользователя.
     */
    @Operation(
            summary = "Получить изображение текущего пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping(value = "/me/image")
    public ResponseEntity<byte[]> getUserImage(
            Authentication authentication
    ) {
        LOGGER.info("Получение изображения пользователя {}", authentication.getName());

        UserEntity user = userService.findUserByName(authentication.getName());
        Avatar image = avatarService.findAvatar(user.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("image/jpeg"));
        httpHeaders.setContentLength(image.getImage().length);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(image.getImage());
    }
}