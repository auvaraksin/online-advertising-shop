package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.mappers.AdsCommentMapper;
import ru.skypro.homework.repositories.AdsCommentRepository;
import ru.skypro.homework.services.AdsCommentService;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class AdsCommentServiceImpl implements AdsCommentService {

    private final AdsCommentRepository adsCommentRepository;
    private final AdsService adsService;
    private final UserService userService;
    private final AdsCommentMapper adsCommentMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsCommentServiceImpl.class);

    public AdsCommentServiceImpl(AdsCommentRepository adsCommentRepository,
                                 AdsService adsService,
                                 UserService userService,
                                 AdsCommentMapper adsCommentMapper) {
        this.adsCommentRepository = adsCommentRepository;
        this.adsService = adsService;
        this.userService = userService;
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
                logger.info("List of all Ads' comments was successfully created");
                return ResponseEntity.ok(responseWrapperAdsCommentDto);
            }
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<AdsCommentDto> addAdsComments(Integer adsId,
                                                        AdsCommentDto adsCommentDto) {
        logger.info("Method to add Ads' comment into DB was invoked");
        if (adsService.findAdsById(adsId).isPresent()
                && userService.findUserById(adsCommentDto.getAuthor()).isPresent()) {
            Ads ads = new Ads();
            User author = new User();
            ads = adsService.findAdsById(adsId).orElseThrow();
            author = userService.findUserById(adsCommentDto.getAuthor()).orElseThrow();
            AdsComment adsComment = adsCommentMapper.adsCommentDtoToAdsComment(adsCommentDto);
            adsComment.setAds(ads);
            adsComment.setAuthor(author);
            adsCommentRepository.save(adsComment);
            logger.info("Comment to Ads with ID = " + adsId + " was successfully added");
            return ResponseEntity.ok(adsCommentDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> deleteAdsComment(Integer adsId, Integer id) {
        logger.info("Method to delete Ads' comment from the DB was invoked");
        if (adsCommentRepository.findAdsCommentByAdsIdAndId(adsId, id) != null) {
            adsCommentRepository.delete(adsCommentRepository.findAdsCommentById(id));
            logger.info("Comment ID = " + id + " was successfully deleted");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<AdsCommentDto> getAdsComment(Integer adsId, Integer id) {
        logger.info("Method to find Ads' comment with ID = " + id + " in the DB was invoked");
        if (adsCommentRepository.findAdsCommentByAdsIdAndId(adsId, id) != null) {
            AdsCommentDto adsCommentDto =
                    adsCommentMapper.adsCommentToAdsCommentDto
                            (adsCommentRepository.findAdsCommentById(id));
            logger.info("Comment ID = " + id + " was successfully found");
            return ResponseEntity.ok(adsCommentDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<AdsCommentDto> updateAdsComment(Integer adsId, Integer id, AdsCommentDto adsCommentDto) {
        logger.info("Method to update Ads' comment with ID = " + id + " in the DB was invoked");
        if (adsCommentRepository.findAdsCommentByAdsIdAndId(adsId, id) != null
                && userService.findUserById(adsCommentDto.getAuthor()).isPresent()) {
            AdsComment adsComment = adsCommentMapper.adsCommentDtoToAdsComment(adsCommentDto);
            adsComment.setId(id);
            adsCommentRepository.save(adsComment);
            logger.info("Comment ID = " + id + " was successfully updated");
            return ResponseEntity.ok(adsCommentDto);
        }
        return ResponseEntity.notFound().build();
    }
}
