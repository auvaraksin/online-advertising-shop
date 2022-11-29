package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsCommentDto;
import ru.skypro.homework.entities.AdsComment;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsCommentMapper {
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    AdsCommentDto adsCommentToAdsCommentDto(AdsComment adsComment);

    List<AdsCommentDto> adsCommentToAdsCommentDto(List<AdsComment> adsComment);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
    AdsComment adsCommentDtoToAdsComment(AdsCommentDto adsCommentDto);

    List<AdsComment> adsCommentDtoToAdsComment(List<AdsCommentDto> adsCommentDto);
}
