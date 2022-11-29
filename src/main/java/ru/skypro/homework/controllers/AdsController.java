package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.FullAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;
import ru.skypro.homework.services.AdsCommentService;
import ru.skypro.homework.services.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Ads Rest Controller", description = "CRUD операции с объявлениями")
public class AdsController {

    private final AdsService adsService;
    private final AdsCommentService adsCommentService;

    /* ---> D O N E <---*/
    @Operation(summary = "getAllAds", description = "Получить все объявления", tags={ "Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseWrapperAdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return adsService.getAllAds();
    }

    /* ---> D O N E <---*/
    @Operation(summary = "addAds",  description = "Добавить объявления", tags={ "Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @PostMapping
    public ResponseEntity<AdsDto> addAds(@RequestBody CreateAdsDto createAdsDto) {
        return adsService.createAds(createAdsDto);
    }

    /* ---> D O N E <---*/
    @Operation(summary = "getAdsMe", description = "Получить объявления", tags={ "Объявления"})///???
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",  content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = ResponseWrapperAdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        return adsService.getAdsMe();
    }

    /* ---> D O N E <---*/
    @Operation(summary = "getAdsComments", description = "Получить все отзывы", tags={ "Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = ResponseWrapperAdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(@PathVariable(name = "ad_pk") Integer adsId) {
        return adsCommentService.getAdsComments(adsId);
    }
    @Operation(summary = "addAdsComments", description = "Сохранить отзыв", tags={ "Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<AdsCommentDto> addAdsComments(@PathVariable(name = "ad_pk") Integer adsId,
                                                        @RequestBody AdsCommentDto dto) {
        return ResponseEntity.ok(new AdsCommentDto());
    }

    @Operation(summary = "deleteAdsComment", description = "Удалить отзыв", tags={ "Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                              @PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "getAdsComment", description = "Получить отзыв", tags={ "Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                                       @PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(new AdsCommentDto());
    }
    @Operation(summary = "updateAdsComment", description = "Обновыть отзыв", tags={ "Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                                          @PathVariable(name = "id") Integer id, @RequestBody AdsCommentDto dto) {
        return ResponseEntity.ok(new AdsCommentDto());
    }

    @Operation(summary = "removeAds",description = "Удалить объявление", tags={ "Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "getAds", description = "Получить полную информацию по объявлению", tags={ "Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = FullAdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(new FullAdsDto());
    }

    @Operation(summary = "updateAds", description = "Обновить объявление", tags={ "Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable Integer id, @RequestBody AdsDto dto) {
        return ResponseEntity.ok(new AdsDto());
    }
}

