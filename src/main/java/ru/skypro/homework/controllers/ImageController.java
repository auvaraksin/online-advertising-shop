package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.services.ImageService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Image Rest Controller", description = "CRUD операции с изображениями")
@RequiredArgsConstructor
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final ImageService imageService;


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
}
