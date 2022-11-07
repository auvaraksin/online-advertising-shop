package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ads;

import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    Optional<Ads> findById(Integer id);
//    List<Ads> findByTitle(String title);

//    List<Ads> findAdsByAuthorOrderById(User author);
}