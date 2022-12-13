package ru.skypro.homework.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.ResponseWrapperUserDto;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.entities.Avatar;
import ru.skypro.homework.entities.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Named(value = "getImageString")
    default String getImageString(UserEntity user) {
        Avatar avatar = user.getAvatar();
        return (avatar == null) ? null : "/users/me/image";
    }

    @Mapping(target = "email", source = "username")
    @Mapping(target = "image", source = "user", qualifiedByName = "getImageString")
    UserDto userToUserDto(UserEntity user);

    @Mapping(target = "username", source = "email")
    @Mapping(target = "avatar", ignore = true)
    UserEntity userDtoToUser(UserDto dto);

    List<UserDto> userListToUserDtoList(List<UserEntity> entityList);

    UserEntity registerReqDtoToUser(RegReqDto dto);

    @Mapping(target = "count", source = "sizeList")
    @Mapping(target = "results", source = "entityList")
    ResponseWrapperUserDto userListToResponseWrapperUserDto(Integer sizeList, List<UserEntity> entityList);
}