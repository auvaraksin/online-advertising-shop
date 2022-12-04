package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entities.Ads;

import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {

    Optional<Ads> findById(Integer id);

//    List<Ads> findByTitle(String title);

//    List<Ads> findAdsByAuthorOrderById(User author);
}