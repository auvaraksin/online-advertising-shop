package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;
import ru.skypro.homework.entities.Ads;

import java.util.Optional;

public interface AdsService {
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();

    ResponseEntity<AdsDto> createAds(CreateAdsDto createAdsDto);

    ResponseEntity<ResponseWrapperAdsDto> getAdsMe();

    Optional<Ads> findAdsById(Integer adsId);
}
