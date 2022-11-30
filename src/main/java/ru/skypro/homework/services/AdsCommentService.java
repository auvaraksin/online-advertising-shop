package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;

public interface AdsCommentService {
    ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(Integer adsId);

    ResponseEntity<AdsCommentDto> addAdsComments(Integer adsId, AdsCommentDto adsCommentDto);

    ResponseEntity<?> deleteAdsComment(Integer adsId, Integer id);

    ResponseEntity<AdsCommentDto> getAdsComment(Integer adsId, Integer id);

    ResponseEntity<AdsCommentDto> updateAdsComment(Integer adsId, Integer id, AdsCommentDto adsCommentDto);
}
