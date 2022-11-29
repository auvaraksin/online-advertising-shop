package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsCommentMapper {
//    @Mapping(source = "author.id", target = "author")
//    @Mapping(source = "pk.id", target = "id")
//    AdsCommentDto adsCommentToAdsCommentDto(AdsComment adsComment);
//
//    List<AdsCommentDto> adsCommentToAdsCommentDto(List<AdsComment> adsComment);
//
//    @Mapping(source = "author", target = "author.id")
//    @Mapping(source = "pk", target = "pk.id")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    AdsComment adsCommentDtoToAdsComment(AdsCommentDto adsCommentDto);
//
//    List<AdsComment> adsCommentDtoToAdsComment(List<AdsCommentDto> adsCommentDto);
}
