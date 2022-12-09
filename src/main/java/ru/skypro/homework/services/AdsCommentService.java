package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.repositories.AdsCommentRepository;

import java.util.List;

@Service
public class AdsCommentService {

    private final AdsCommentRepository adsCommentRepository;

    public AdsCommentService(AdsCommentRepository adsCommentRepository) {
        this.adsCommentRepository = adsCommentRepository;
    }

    /**
     * Возвращает отзыв для объявления по указанному ID.
     *
     * @param id ID отзыва.
     * @param idAds ID объявления.
     * @return отзыв.
     * @throws NotFoundException отзыв с указанным ID отсутствует в базе.
     */
    public AdsComment findAdsComment(long id, long idAds) {
        return adsCommentRepository.findFirstByIdAndAdsId(id, idAds).orElseThrow(
                () -> new NotFoundException("Comment not found"));
    }

    /**
     * Возвращает все отзывы для объявления.
     *
     * @param idAds ID объявления.
     * @return список всех отзывов для объявления.
     */
    public List<AdsComment> findAllAdsComments(long idAds) {
        return adsCommentRepository.findAllByAdsId(idAds);
    }

    /**
     * Создает новый отзыв.
     *
     * @param adsComment новый отзыв.
     * @return созданный отзыв.
     */
    public AdsComment createAdsComment(AdsComment adsComment) {
        adsComment.setId(null);
        return adsCommentRepository.save(adsComment);
    }

    /**
     * Обновляет содержание отзыва (поле text).
     *
     * @param adsComment обновленные данные отзыва.
     * @param id ID отзыва.
     * @param idAds ID объявления.
     * @return обновленный отзыв.
     * @throws NotFoundException отзыв с указанными параметрами отсутствует в базе.
     */
    public AdsComment updateAdsComment(AdsComment adsComment, long id, long idAds) {
        AdsComment adsCommentBD = findAdsComment(id, idAds);
        adsCommentBD.setText(adsComment.getText());

        return adsCommentRepository.save(adsCommentBD);
    }

    /**
     * Удаляет отзыв.
     *
     * @param id ID отзыва, который должен быть удален.
     */
    public void deleteAdsComment(long id) {
        adsCommentRepository.deleteById(id);
    }
}