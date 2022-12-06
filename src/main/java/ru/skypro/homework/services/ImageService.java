package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.utility.ImageUtility;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository ImageRepository;

    public ImageService(ImageRepository adsImageRepository) {
        this.ImageRepository = adsImageRepository;
    }

    /**
     * Возвращает изображение по указанному ID.
     *
     * @param id ID изображения.
     * @return изображение.
     * @throws NotFoundException изображение с указанным ID отсутствует в базе.
     */
    public Image findImage(Long id) {
        return ImageRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Image not found"));
    }

    /**
     * Создает новое изображение для объявления.
     *
     * @param ads  объявление.
     * @param file файл изображения.
     * @return ID созданного изображения.
     * @throws NotFoundException не удалось извлечь содержимое изображения.
     */
    public String createImage(Ads ads, MultipartFile file) {
        byte[] imageContent;
        try {
            imageContent = ImageUtility.getImageContent(file);
        } catch (IOException e) {
            throw new NotFoundException("Failed to extract image contents");
        }

        Image adsImage = new Image();
        adsImage.setAds(ads);
        adsImage.setImage(imageContent);
        return ImageRepository.save(adsImage).getId().toString();
    }
}