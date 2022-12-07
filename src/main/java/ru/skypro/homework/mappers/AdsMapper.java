package ru.skypro.homework.mappers;

import org.mapstruct.*;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.dtos.FullAdsDto;
import ru.skypro.homework.dtos.ResponseWrapperAdsDto;
import ru.skypro.homework.entities.Ads;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdsMapper {

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "images", ignore = true)
    Ads adsDtoToAds(AdsDto ads);

    @Mapping(target = "count", source = "sizeList")
    @Mapping(target = "results", source = "entityList")
    ResponseWrapperAdsDto adsListToResponseWrapperAdsDto(Integer sizeList, List<Ads> entityList);

    List<AdsDto> adsListToAdsDtoList(List<Ads> adsList);

    Ads createAdsDtoToAds(CreateAdsDto dto);

    CreateAdsDto adsToCreateAdsDto(Ads entity);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "email", source = "author.username")
    @Mapping(target = "phone", source = "author.phone")
    FullAdsDto adsToFullAdsDto(Ads ads);
}