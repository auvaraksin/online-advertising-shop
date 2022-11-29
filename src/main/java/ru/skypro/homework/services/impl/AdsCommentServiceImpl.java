package ru.skypro.homework.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.repositories.AdsCommentRepository;
import ru.skypro.homework.services.AdsCommentService;

@Service
public class AdsCommentServiceImpl implements AdsCommentService {

    private final AdsCommentRepository adsCommentRepository;

    private final AdsMapper adsMapper;

    private static final Logger logger = LoggerFactory.getLogger(AdsCommentServiceImpl.class);

    public AdsCommentServiceImpl(AdsCommentRepository adsCommentRepository, AdsMapper adsMapper) {
        this.adsCommentRepository = adsCommentRepository;
        this.adsMapper = adsMapper;
    }
}
