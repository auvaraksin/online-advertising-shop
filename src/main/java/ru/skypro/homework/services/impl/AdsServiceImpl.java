package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.repositories.AdsRepository;

@Service
public class AdsServiceImpl {

    private final AdsRepository adsRepository;

    private final AdsMapper adsMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    public AdsServiceImpl(AdsRepository adsRepository, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.adsMapper = adsMapper;
    }
}