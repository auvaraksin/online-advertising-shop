package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.AdsDto;
import ru.skypro.homework.dtos.CreateAdsDto;
import ru.skypro.homework.entities.Ads;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdsMapper {

    @Mapping(source = "ads.id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    AdsDto adsToAdsDto(Ads ads);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
    Ads adsDtoToAds(AdsDto adsDto);

    List<AdsDto> adsListToAdsDtoList(List<Ads> adsList);

    List<Ads> adsDtoToAds(List<AdsDto> adsDto);

//    @Mapping(target = "author", ignore = true)
//    @Mapping(target = "pk", ignore = true)
    Ads createAdsToAds(CreateAdsDto createAdsDto);

}