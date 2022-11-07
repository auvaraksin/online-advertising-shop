package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.CreateUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
}