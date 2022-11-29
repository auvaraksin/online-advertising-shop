package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;

public interface AdsService {
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();

    ResponseEntity<AdsDto> createAds(CreateAdsDto createAdsDto);

    ResponseEntity<ResponseWrapperAdsDto> getAdsMe();
}
