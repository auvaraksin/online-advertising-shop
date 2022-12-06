package ru.skypro.homework.services;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.repositories.AdsRepository;

import java.util.List;

@Service
public class AdsService {

    private final AdsRepository adsRepository;

    public AdsService(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    /**
     * Возвращает объявление по указанному ID.
     *
     * @param id ID объявления.
     * @return объявление.
     * @throws NotFoundException объявление с указанным ID отсутствует в базе.
     */
    public Ads findAds(long id) {
        return adsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Ads not found"));
    }

    /**
     * Возвращает все объявления.
     *
     * @return список всех объявлений.
     */
    public List<Ads> findAllAds() {
        return adsRepository.findAll();
    }

    /**
     * Возвращает все объявления автора.
     *
     * @param user автор.
     * @return список всех объявлений автора.
     */
    public List<Ads> findAllAdsByAuthor(UserEntity user) {
        return adsRepository.findAllByAuthor(user);
    }

    /**
     * Создает новое объявление.
     *
     * @param ads новое объявление.
     * @return созданное объявление.
     */
    public Ads createAds(Ads ads) {
        ads.setId(null);
        return adsRepository.save(ads);
    }

    /**
     * Обновляет содержание объявления (поля price и title).
     *
     * @param ads обновленные данные объявления.
     * @param id  ID объявления.
     * @return обновленное объявление.
     * @throws NotFoundException объявление с указанными параметрами отсутствует в базе.
     */
    public Ads updateAds(Ads ads, long id) {
        Ads adsBd = findAds(id);
        adsBd.setId(id);
        adsBd.setPrice(ads.getPrice());
        adsBd.setTitle(ads.getTitle());
        adsBd.setDescription(ads.getDescription());

        return adsRepository.save(adsBd);
    }

    /**
     * Удаляет объявление.
     *
     * @param id ID объявления, которое должно быть удалено.
     */
    public void deleteAds(long id) {
        adsRepository.deleteById(id);
    }
}