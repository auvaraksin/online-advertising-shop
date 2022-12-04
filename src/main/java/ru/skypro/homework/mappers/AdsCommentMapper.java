package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.entities.AdsComment;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsCommentMapper {
    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "ads.id", target = "pk")
    AdsCommentDto adsCommentToAdsCommentDto(AdsComment adsComment);

    List<AdsCommentDto> adsCommentToAdsCommentDto(List<AdsComment> adsComment);

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "pk", target = "ads.id")
    AdsComment adsCommentDtoToAdsComment(AdsCommentDto adsCommentDto);

    List<AdsComment> adsCommentDtoToAdsComment(List<AdsCommentDto> adsCommentDto);
}
