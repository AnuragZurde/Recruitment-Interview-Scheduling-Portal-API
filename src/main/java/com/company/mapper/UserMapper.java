package com.company.mapper;

import com.company.dto.userProfileDto.UserProfileResponseDto;
import com.company.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "role", source = "userRole")
    UserProfileResponseDto toDto(User user);
}
