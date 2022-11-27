package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entities.AdsComment;

import java.util.List;

@Repository
public interface AdsCommentRepository extends JpaRepository<AdsComment, Integer> {

//    @Query("SELECT * FROM AdsComment INNER JOIN Ads ON ads_id = id")
    List<AdsComment> findAdsCommentByAdsId(Integer id);
}