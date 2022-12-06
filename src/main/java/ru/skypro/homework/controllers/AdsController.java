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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.FullAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.mappers.AdsCommentMapper;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.services.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Ads Rest Controller", description = "CRUD операции с объявлениями")
public class AdsController {

    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsService adsService;
    private final AdsCommentService adsCommentService;
    private final UserService userService;
    private final ImageService imageService;
    private final AuthService authService;

    private final AdsMapper adsMapper;
    private final AdsCommentMapper adsCommentMapper;

    @Autowired
    public AdsController(AdsMapper adsMapper,
                         AdsCommentMapper adsCommentMapper,
                         UserService userService,
                         AdsService adsService,
                         AdsCommentService adsCommentService,
                         ImageService imageService,
                         AuthService authService) {
        this.adsMapper = adsMapper;
        this.adsCommentMapper = adsCommentMapper;
        this.userService = userService;
        this.adsService = adsService;
        this.adsCommentService = adsCommentService;
        this.imageService = imageService;
        this.authService = authService;
    }

    /**
     * Добавление объявления.
     */
    @Operation(summary = "addAds", description = "Добавить объявления", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<AdsDto> addAds(Authentication authentication,
                                         @Parameter(description = "Параметры объявления")
                                         @RequestPart("properties") @Valid CreateAdsDto createAdsDto,
                                         @Parameter(description = "Изображение")
                                         @RequestPart("image") MultipartFile file
    ) {
        logger.info("Добавление объявления: {}", createAdsDto);

        UserEntity author = userService.findUserByName(authentication.getName());
        Ads ads = adsMapper.createAdsDtoToAds(createAdsDto);
        ads.setAuthor(author);
        Ads adsCreated = adsService.createAds(ads);

        String imageId = imageService.createImage(adsCreated, file);
        AdsDto adsDto = adsMapper.adsToAdsDto(adsCreated);
        adsDto.setImage("/ads/image/" + imageId);

        return ResponseEntity.ok(adsDto);
    }

    /**
     * Получение списка всех объявлений.
     */
    @Operation(summary = "getAllAds", description = "Получить все объявления", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        logger.info("Получение списка объявлений");

        List<Ads> adsList = adsService.findAllAds();

        return ResponseEntity.ok(adsMapper
                .adsListToResponseWrapperAdsDto(adsList.size(), adsList));
    }

    /**
     * Получение списка объявлений авторизованного пользователя.
     */
    @Operation(summary = "getAdsMe", description = "Получить объявления", tags = {"Объявления"})///???
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe(
            Authentication authentication,
            @RequestParam(required = false) Boolean authenticated,
            @RequestParam(required = false) String authorities0Authority,
            @RequestParam(required = false) Object credentials,
            @RequestParam(required = false) Object details,
            @RequestParam(required = false) Object principal
    ) {
        logger.info("Получение списка объявлений авторизованного пользователя");

        UserEntity user = userService.findUserByName(authentication.getName());
        List<Ads> adsList = adsService.findAllAdsByAuthor(user);

        return ResponseEntity.ok(adsMapper
                .adsListToResponseWrapperAdsDto(adsList.size(), adsList));
    }

    /**
     * Получение объявления по Id.
     */
    @Operation(
            summary = "Получить объявление по ID",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("{id}")
    public ResponseEntity<FullAdsDto> getAds(
            @Parameter(description = "ID объявления")
            @PathVariable("id") Integer id
    ) {
        logger.info("Получение объявления {}", id);

        Ads ads = adsService.findAds(id);

        return ResponseEntity.ok(adsMapper.adsToFullAdsDto(ads));
    }

    /**
     * Удаление объявления.
     */
    @Operation(summary = "removeAds", description = "Удалить объявление", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeAds(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable("id") Integer id
    ) {
        logger.info("Удаление объявления {}", id);

        UserEntity author = adsService.findAds(id).getAuthor();
        if (!authService.hasRole(authentication.getName(), UserEntity.UserRole.ADMIN.name()) &&
                !authentication.getName().equals(author.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        adsService.deleteAds(id);

        return ResponseEntity.ok(null);
    }

    /**
     * Обновление объявления.
     */
    @Operation(summary = "updateAds", description = "Обновить объявление", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping(value = "{id}")
    public ResponseEntity<AdsDto> updateAds(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable("id") Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры объявления")
            @RequestBody @Valid CreateAdsDto createAdsDto
    ) {
        logger.info("Обновление объявления {} : {}", id, createAdsDto);

        Ads ads = adsService.findAds(id);

        UserEntity author = ads.getAuthor();
        if (!authService.hasRole(authentication.getName(), UserEntity.UserRole.ADMIN.name()) &&
                !authentication.getName().equals(author.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Ads adsUpdated = adsService.updateAds(adsMapper.createAdsDtoToAds(createAdsDto), id);
        return ResponseEntity.ok(adsMapper.adsToAdsDto(adsUpdated));
    }

    /**
     * Добавление отзыва.
     */
    @Operation(
            summary = "Добавить отзыв",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))
            }
    )
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("{ad_pk}/comments")
    public ResponseEntity<AdsCommentDto> addAdsComments(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable("ad_pk") String adPk,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры отзыва")
            @RequestBody AdsCommentDto adsCommentDto
    ) {
        logger.info("Добавление отзыва: {}", adsCommentDto);

        UserEntity author = userService.findUserByName(authentication.getName());
        Ads ads = adsService.findAds(Long.parseLong(adPk));
        AdsComment adsComment = adsCommentMapper.adsCommentDtoToAdsComment(adsCommentDto);
        adsComment.setAuthor(author);
        adsComment.setAds(ads);
        adsComment.setCreatedAt(LocalDateTime.now());
        AdsComment adsCommentCreated = adsCommentService.createAdsComment(adsComment);

        return ResponseEntity.ok(adsCommentMapper.adsCommentToAdsCommentDto(adsCommentCreated));
    }

    /**
     * Удаление отзыва.
     */
    @Operation(summary = "deleteAdsComment", description = "Удалить отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteAdsComment(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable("ad_pk") String adPk,
            @Parameter(description = "ID отзыва")
            @PathVariable("id") Integer id
    ) {
        logger.info("Удаление отзыва {}", id);

        UserEntity authorComment = adsCommentService.findAdsComment(id, Long.parseLong(adPk)).getAuthor();
        if (!authService.hasRole(authentication.getName(), UserEntity.UserRole.ADMIN.name()) &&
                !authentication.getName().equals(authorComment.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        adsCommentService.deleteAdsComment(id);

        return ResponseEntity.ok(null);
    }

    /**
     * Обновление отзыва.
     */
    @Operation(summary = "updateAdsComment", description = "Обновить отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable("ad_pk") String adPk,
            @Parameter(description = "ID отзыва")
            @PathVariable("id") Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Параметры отзыва")
            @RequestBody AdsCommentDto adsCommentDto
    ) {
        logger.info("Обновление отзыва {} : {}", id, adsCommentDto);

        UserEntity authorComment = adsCommentService.findAdsComment(id, Long.parseLong(adPk)).getAuthor();
        if (!authService.hasRole(authentication.getName(), UserEntity.UserRole.ADMIN.name()) &&
                !authentication.getName().equals(authorComment.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        AdsComment adsCommentUpdated = adsCommentService
                .updateAdsComment(adsCommentMapper.adsCommentDtoToAdsComment(adsCommentDto), id, Long.parseLong(adPk));

        return ResponseEntity.ok(adsCommentMapper.adsCommentToAdsCommentDto(adsCommentUpdated));
    }

    /**
     * Получение отзыва по Id.
     */
    @Operation(
            summary = "Получить отзыв по ID",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(
            @Parameter(description = "ID объявления")
            @PathVariable("ad_pk") String adPk,
            @Parameter(description = "ID отзыва")
            @PathVariable("id") Integer id
    ) {
        logger.info("Получение отзыва {}", id);

        AdsComment adsComment = adsCommentService.findAdsComment(id, Long.parseLong(adPk));

        return ResponseEntity.ok(adsCommentMapper.adsCommentToAdsCommentDto(adsComment));
    }

    /**
     * Получение списка отзывов.
     */

    @Operation(summary = "getAdsComments", description = "Получить все отзывы", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAllAdsComments(
            @Parameter(description = "ID объявления")
            @PathVariable("ad_pk") String adPk
    ) {
        logger.info("Получение списка отзывов для объявления {}", adPk);

        List<AdsComment> adsCommentList = adsCommentService.findAllAdsComments(Long.parseLong(adPk));

        return ResponseEntity.ok(adsCommentMapper
                .adsCommentListToResponseWrapperAdsCommentDto(adsCommentList.size(), adsCommentList));
    }

    /**
     * Получение изображения по Id.
     */
    @Operation(
            summary = "Получить изображение по ID",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping(value = "/image/{pk}")
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "ID изображения")
            @PathVariable("pk") Integer pk
    ) {
        logger.info("Получение изображения {}", pk);

        Image adsImage = imageService.findImage(Long.valueOf(pk));

        return ResponseEntity.status(HttpStatus.OK).body(adsImage.getImage());
    }

    /**
     * Добавление изображения.
     */
    @Operation(
            summary = "Добавить изображение",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*")),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAdsImage(
            Authentication authentication,
            @Parameter(description = "ID объявления")
            @PathVariable Integer id,
            @Parameter(description = "Изображение")
            @RequestParam MultipartFile image
    ) {
        logger.info("Добавление изображения для объявления {}", id);

        Ads ads = adsService.findAds(id);

        UserEntity author = ads.getAuthor();
        if (!authService.hasRole(authentication.getName(), UserEntity.UserRole.ADMIN.name()) &&
                !authentication.getName().equals(author.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        imageService.createImage(ads, image);

        return ResponseEntity.ok(null);
    }
}