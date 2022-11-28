package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.services.AdsService;
import ru.skypro.homework.services.UserService;

import javax.transaction.Transactional;
import java.util.List;
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
            Ads ads = adsMapper.createAdsToAds(createAdsDto);
            User user = userService.findUserByEmail(auth.getName()).orElseThrow();
            ads.setAuthor(user);
            //  TODO: Create method to add image into db
            ads.setImage("TODO: Create method to add image into db");
            adsRepository.save(ads);
            logger.info("Ads_id = " + ads.getId() + " has been successfully created and recorded into DB in the table 'Ads'");
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
            return ResponseEntity.ok(responseWrapperAdsDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}