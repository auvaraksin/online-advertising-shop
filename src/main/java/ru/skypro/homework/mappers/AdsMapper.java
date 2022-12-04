package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.FullAdsDto;
import ru.skypro.homework.entities.Ads;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsMapper {

    @Mapping(target = "pk", source = "ads.id")
    @Mapping(target = "author", source = "author.id")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "description", ignore = true)
    Ads adsDtoToAds(AdsDto adsDto);

    List<AdsDto> adsListToAdsDtoList(List<Ads> adsList);

    List<Ads> adsDtoListToAdsList(List<AdsDto> adsDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "adsCommentList", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads createAdsDtoToAds(CreateAdsDto createAdsDto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "phone", source = "author.phone")
    FullAdsDto adsToFullAdsDto(Ads ads);
}