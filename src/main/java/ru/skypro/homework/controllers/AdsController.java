package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.SpringVersion;
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
import ru.skypro.homework.services.AdsCommentService;
import ru.skypro.homework.services.AdsService;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name = "Ads Rest Controller", description = "CRUD операции с объявлениями")
public class AdsController {

    private final AdsService adsService;
    private final AdsCommentService adsCommentService;

    @Operation(summary = "getAllAds", description = "Получить все объявления", tags = {"Объявления"})
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

    @Operation(summary = "addAds", description = "Добавить объявления", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @PostMapping
//            (produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
//                    MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<AdsDto> addAds(@RequestPart("properties") CreateAdsDto createAdsDto) {
        public ResponseEntity<AdsDto> addAds(@RequestBody CreateAdsDto createAdsDto) {
//            ,
//                                         @RequestPart("image") List<MultipartFile> multipartFileList) {
        return adsService.createAds(createAdsDto);
//                , multipartFileList);
    }

    @Operation(summary = "getAdsMe", description = "Получить объявления", tags = {"Объявления"})///???
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = ResponseWrapperAdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        return adsService.getAdsMe();
    }

    @Operation(summary = "getAdsComments", description = "Получить все отзывы", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = ResponseWrapperAdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(@PathVariable(name = "ad_pk") Integer adsId) {
        return adsCommentService.getAdsComments(adsId);
    }

    @Operation(summary = "addAdsComments", description = "Сохранить отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<AdsCommentDto> addAdsComments(@PathVariable(name = "ad_pk") Integer adsId,
                                                        @RequestBody AdsCommentDto adsCommentDto) {
        return adsCommentService.addAdsComments(adsId, adsCommentDto);
    }

    @Operation(summary = "deleteAdsComment", description = "Удалить отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("@userServiceImpl.getUser(@adsCommentServiceImpl.getAdsComment(#adsId, #id).body.author).body.email" +
            ".equals(authentication.principal.username) or hasAuthority('ADMIN')")
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                              @PathVariable(name = "id") Integer id) {
        return adsCommentService.deleteAdsComment(adsId, id);
    }

    @Operation(summary = "getAdsComment", description = "Получить отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                                       @PathVariable(name = "id") Integer id) {
        return adsCommentService.getAdsComment(adsId, id);
    }

    @Operation(summary = "updateAdsComment", description = "Обновыть отзыв", tags = {"Отзывы"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsCommentDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("@userServiceImpl.getUser(@adsCommentServiceImpl.getAdsComment(#adsId, #id).body.author).body.email" +
            ".equals(authentication.principal.username) or hasAuthority('ADMIN')")
    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> updateAdsComment(@PathVariable(name = "ad_pk") Integer adsId,
                                                          @PathVariable(name = "id") Integer id,
                                                          @RequestBody AdsCommentDto adsCommentDto) {
        return adsCommentService.updateAdsComment(adsId, id, adsCommentDto);
    }

    @Operation(summary = "removeAds", description = "Удалить объявление", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("@userServiceImpl.getUser(@adsCommentServiceImpl.getAdsComment(#adsId, #id).body.author).body.email" +
            ".equals(authentication.principal.username) or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable Integer id) {
        return adsService.removeAds(id);
    }

    @Operation(summary = "getAds", description = "Получить полную информацию по объявлению", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = FullAdsDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAds(@PathVariable(name = "id") Integer adsId) {
        return adsService.getAds(adsId);
    }

    @Operation(summary = "updateAds", description = "Обновить объявление", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "*/*",
                    schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = ""))})
    @PreAuthorize("@userServiceImpl.getUser(@adsCommentServiceImpl.getAdsComment(#adsId, #id).body.author).body.email" +
            ".equals(authentication.principal.username) or hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable(name = "id") Integer id, @RequestBody AdsDto adsDto) {
        return adsService.updateAds(id, adsDto);
    }
}
