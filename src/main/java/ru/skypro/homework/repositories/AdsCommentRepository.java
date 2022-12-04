package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsCommentRepository extends JpaRepository<AdsComment, Integer> {

    List<AdsComment> findAdsCommentByAdsId(Integer adsId);

    AdsComment findAdsCommentById(Integer id);

    AdsComment findAdsCommentByAdsIdAndId(Integer adsId, Integer id);

    AdsComment findAdsCommentByAdsAndAuthor(Optional<Ads> ads, Optional<User> author);
}