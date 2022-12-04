package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.services.ImageService;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ResponseEntity<Image> getImageById(Integer id) {
        logger.info("Method to find record with image data by ID = " + id + " in the DB in table 'images' was invoked");
        if (imageRepository.findById(id).isPresent()) {
            Image image = imageRepository.findById(id).orElseThrow();
            logger.info("Image with ID = " + id + "was successfully found");
            return ResponseEntity.ok(image);
        }
        logger.info("Image with ID = " + id + "does not exist");
        return ResponseEntity.notFound().build();
    }

    public Image saveImage(Ads ads, MultipartFile file) {
        logger.info("Method to create a new image in the DB in table 'images' was invoked");
        logger.info("Ads ID = " + ads.getId()
                + ", Ads author email = " + ads.getAuthor().getEmail()
                + ", Ads image = " + ads.getImage());
        logger.info("File name = " + file.getOriginalFilename() + ", file type = " + file.getContentType());
        if (!file.isEmpty()) {
        Image image = new Image();
        image.setAds(ads);
        image.setFileSize(file.getSize());
        image.setMediaType(file.getContentType());
        try {
            image.setPreview(file.getBytes());
        } catch (IOException e) {
            logger.info("File is missed");
            e.printStackTrace();
        }
        imageRepository.save(image);
        image.setFilePath(String.format("/images/" + image.getId()));
        logger.info("Image was successfully saved. File path is " + image.getFilePath());
        return imageRepository.save(image);
        }
        logger.info("Ads or file is empty");
        return imageRepository.save(new Image());
    }
}
