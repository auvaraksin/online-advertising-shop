package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.*;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserService userService;
    private final AdsMapper adsMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    public AdsServiceImpl(AdsRepository adsRepository,
                          UserService userService,
                          AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.userService = userService;
        this.adsMapper = adsMapper;
    }

    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        logger.info("Method to find all ads in the DB in table 'Ads' was invoked");
        List<Ads> adsList = adsRepository.findAll();
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<AdsDto> adsDtoList = adsMapper.adsListToAdsDtoList(adsList);
        responseWrapperAdsDto.setCount(adsDtoList.size());
        responseWrapperAdsDto.setResults(adsDtoList);
        return ResponseEntity.ok(responseWrapperAdsDto);
    }

    public ResponseEntity<AdsDto> createAds(CreateAdsDto createAdsDto) {
        logger.info("Method to create a new ads in the DB in table 'Ads' was invoked");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (createAdsDto != null) {
            Ads ads = adsMapper.createAdsDtoToAds(createAdsDto);
            User user = userService.findUserByEmail(auth.getName()).orElseThrow();
            ads.setAuthor(user);
            //  TODO: Create method to add image into db
            ads.setImage("TODO: Create method to add image into db");
            adsRepository.save(ads);
            logger.info("Ads ID = " + ads.getId() + " has been successfully created and recorded into DB in the table 'Ads'");
            AdsDto adsDto = adsMapper.adsToAdsDto(ads);
            return ResponseEntity.ok(adsDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        logger.info("Method to find all user's ads in the DB in table 'Ads' was invoked");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).orElseThrow();
        List<Ads> adsList = adsRepository.findAll()
                .stream()
                .filter(ads -> ads.getAuthor().equals(user))
                .collect(Collectors.toList());
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        if (!adsList.isEmpty()) {
            List<AdsDto> adsDtoList = adsMapper.adsListToAdsDtoList(adsList);
            responseWrapperAdsDto.setCount(adsDtoList.size());
            responseWrapperAdsDto.setResults(adsDtoList);
            logger.info("All user's ads were successfully found");
            return ResponseEntity.ok(responseWrapperAdsDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Ads> findAdsById(Integer adsId) {
        logger.info("Method to find all ads by its ID in the DB in table 'Ads' was invoked");
        return adsRepository.findById(adsId);
    }

    public ResponseEntity<?> removeAds(Integer adsId) {
        logger.info("Method to delete ads by its ID from the DB was invoked");
        if (adsRepository.findById(adsId).isPresent()) {
            adsRepository.delete(adsRepository.findById(adsId).orElseThrow());
            logger.info("Ads ID = " + adsId + " was successfully deleted");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<FullAdsDto> getAds(Integer adsId) {
        logger.info("Method to find ads by its ID in the DB was invoked");
        if (adsRepository.findById(adsId).isPresent()) {
            FullAdsDto fullAdsDto = adsMapper.adsToFullAdsDto(adsRepository.findById(adsId).orElseThrow());
            logger.info("Ads ID = " + adsId + " was successfully found");
            return ResponseEntity.ok(fullAdsDto);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<AdsDto> updateAds(Integer adsId, AdsDto adsDto) {
        logger.info("Method to update ads by its ID in the DB was invoked");
        if (adsRepository.findById(adsId).isPresent()) {
            Ads ads = adsMapper.adsDtoToAds(adsDto);
            ads.setDescription(adsRepository.findById(adsId).orElseThrow().getDescription());
            adsRepository.save(ads);
            logger.info("Ads ID = " + adsId + " was successfully updated");
            return ResponseEntity.ok(adsDto);
        }
        return ResponseEntity.notFound().build();
    }
}