package ru.skypro.homework.services;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;

public interface AdsCommentService {
    ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(Integer adsId);
}
