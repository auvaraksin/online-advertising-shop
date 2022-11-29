package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.mappers.AdsCommentMapper;
import ru.skypro.homework.repositories.AdsCommentRepository;
import ru.skypro.homework.services.AdsCommentService;
import ru.skypro.homework.services.AdsService;

import java.util.List;
import java.util.Optional;

@Service
public class AdsCommentServiceImpl implements AdsCommentService {

    private final AdsCommentRepository adsCommentRepository;
    private final AdsService adsService;

    private final AdsCommentMapper adsCommentMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsCommentServiceImpl.class);

    public AdsCommentServiceImpl(AdsCommentRepository adsCommentRepository,
                                 AdsService adsService,
                                 AdsCommentMapper adsCommentMapper) {
        this.adsCommentRepository = adsCommentRepository;
        this.adsService = adsService;
        this.adsCommentMapper = adsCommentMapper;
    }

    public ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(Integer adsId) {
        logger.info("Method to find all Ads' comments in the DB was invoked");
        Optional<Ads> ads = adsService.findAdsById(adsId);
        if (ads.isPresent()) {
            List<AdsComment> adsCommentList = adsCommentRepository.findAdsCommentByAdsId(adsId);
            if (!adsCommentList.isEmpty()) {
                List<AdsCommentDto> adsCommentDtoList = adsCommentMapper.adsCommentToAdsCommentDto(adsCommentList);
                ResponseWrapperAdsCommentDto responseWrapperAdsCommentDto = new ResponseWrapperAdsCommentDto();
                responseWrapperAdsCommentDto.setCount(adsCommentDtoList.size());
                responseWrapperAdsCommentDto.setResults(adsCommentDtoList);
                return ResponseEntity.ok(responseWrapperAdsCommentDto);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
