package com.divefinder.helpers;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import com.divefinder.models.User;
import com.divefinder.models.UserDto;

@Component
public class UserDtoMapper {

    public UserDto toDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();


        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAdmin(user.isAdmin());

        return dto;
    }


    public User toUser(UserDto dto) {
        if (dto == null) return null;
        User user = new User();


        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAdmin(dto.isAdmin());

        return user;
    }


}
