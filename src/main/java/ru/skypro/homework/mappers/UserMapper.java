package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dtos.CreateUserDto;
import ru.skypro.homework.dtos.RegReqDto;
import ru.skypro.homework.dtos.UserDto;
import ru.skypro.homework.entities.User;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto userToUserDto(User user);

    List<UserDto> usersToUsersDto(List<User> userList);

//    @Mapping(target = "roles", ignore = true)
//    @Mapping(target = "adsList", ignore = true)
//    @Mapping(target = "adsCommentList", ignore = true)
//    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    /* see mapstruct documentation */
//    List<User> usersDtoToUsers(List<UserDto> userDtoList);

//    @Mapping(target = "id", ignore = true)
////    @Mapping(target = "roles", ignore = true)
//    @Mapping(target = "adsList", ignore = true)
//    @Mapping(target = "adsCommentList", ignore = true)
    User createUserDtoToUser(CreateUserDto createUserDto);

    @Mapping(source = "username", target = "email")
    User regReqDtoToUser(RegReqDto regReqDto);
}