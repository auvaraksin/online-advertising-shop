package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entities.Avatar;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.utility.ImageUtility;

import java.io.IOException;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    /**
     * Возвращает аватар по указанному ID.
     *
     * @param id ID аватара.
     * @return аватар.
     * @throws NotFoundException аватар с указанным ID отсутствует в базе.
     */
    public Avatar findAvatar(Long id) {
        return avatarRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User avatar not found"));
    }

    /**
     * Обновляет аватар пользователя.
     *
     * @param user пользователь.
     * @param file файл изображения.
     * @throws NotFoundException не удалось извлечь содержимое изображения.
     */
    public void updateAvatar(UserEntity user, MultipartFile file) {
        byte[] imageContent;
        try {
            imageContent = ImageUtility.getThumbnailImageContent(file);
        } catch (IOException e) {
            throw new NotFoundException("Failed to extract avatar contents");
        }

        Avatar avatarBD = avatarRepository.findById(user.getId()).orElse(new Avatar());
        avatarBD.setUser(user);
        avatarBD.setImage(imageContent);

        avatarRepository.save(avatarBD);
    }
}