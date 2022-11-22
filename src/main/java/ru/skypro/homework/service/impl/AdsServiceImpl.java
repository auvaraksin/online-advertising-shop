package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;

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