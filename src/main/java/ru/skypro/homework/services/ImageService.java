package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.Image;

public interface ImageService {
    ResponseEntity<Image> getImageById(Integer id);
    Image saveImage(Ads ads, MultipartFile file);
}
