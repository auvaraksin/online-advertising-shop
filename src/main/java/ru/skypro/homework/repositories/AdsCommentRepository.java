package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsCommentRepository extends JpaRepository<AdsComment, Long> {

    Optional<AdsComment> findFirstByIdAndAdsId(Long id, Long idAds);

    List<AdsComment> findAllByAdsId(Long idAds);
}