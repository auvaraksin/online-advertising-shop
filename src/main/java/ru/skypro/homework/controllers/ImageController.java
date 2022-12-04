package ru.skypro.homework.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.services.ImageService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "Image Rest Controller", description = "CRUD операции с изображениями")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "getImage", description = "Получить все объявления", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = Image.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "")),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = ""))})
    @GetMapping(value = "/{Id}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable (name = "id") Integer id) {
        Image image = imageService.getImageById(id).getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(image.getPreview().length);
        return ResponseEntity.status(200).headers(headers).body(image.getPreview());
    }
}
