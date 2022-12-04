package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.entities.Ads;

import java.util.List;
import java.util.Optional;

public interface AdsService {
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();

    ResponseEntity<AdsDto> createAds(CreateAdsDto createAdsDto);
//            , List<MultipartFile> multipartFileList);

    ResponseEntity<ResponseWrapperAdsDto> getAdsMe();

    Optional<Ads> findAdsById(Integer adsId);

    ResponseEntity<?> removeAds(Integer adsId);

    ResponseEntity<FullAdsDto> getAds(Integer adsId);

    ResponseEntity<AdsDto> updateAds(Integer adsId, AdsDto adsDto);
}
