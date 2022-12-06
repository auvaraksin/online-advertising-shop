package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    Optional<Ads> findById(long id);

    List<Ads> findAllByAuthor(UserEntity user);

    List<Ads> findAllByTitleLikeIgnoreCase(String title);
}