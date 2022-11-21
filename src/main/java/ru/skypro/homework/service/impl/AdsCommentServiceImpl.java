package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsCommentRepository;

@Service
public class AdsCommentServiceImpl {

    private final AdsCommentRepository adsCommentRepository;

    private final AdsMapper adsMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsCommentServiceImpl.class);

    public AdsCommentServiceImpl(AdsCommentRepository adsCommentRepository, AdsMapper adsMapper) {
        this.adsCommentRepository = adsCommentRepository;
        this.adsMapper = adsMapper;
    }
}
