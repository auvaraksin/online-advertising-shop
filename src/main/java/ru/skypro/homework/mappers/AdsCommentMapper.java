package ru.skypro.homework.mappers;

import org.mapstruct.*;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsCommentDto;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.Image;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsCommentMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "createdAt", source = "createdAt")
    AdsCommentDto adsCommentToAdsCommentDto(AdsComment adsComment);

    List<AdsCommentDto> adsCommentToAdsCommentDto(List<AdsComment> adsComment);

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "createdAt", source = "createdAt")
    AdsComment adsCommentDtoToAdsComment(AdsCommentDto adsCommentDto);

    @Mapping(target = "count", source = "sizeList")
    @Mapping(target = "results", source = "entityList")
    ResponseWrapperAdsCommentDto adsCommentListToResponseWrapperAdsCommentDto(Integer sizeList, List<AdsComment> entityList);
}