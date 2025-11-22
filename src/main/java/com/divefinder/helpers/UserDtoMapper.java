package com.divefinder.helpers;

import com.divefinder.models.UserRegisterDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import com.divefinder.models.User;
import com.divefinder.models.UserDto;
import com.divefinder.models.UserLoginDto;

@Component
public class UserDtoMapper {

    public UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();


        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAdmin(user.isAdmin());

        return dto;
    }


    public User userDtoToUser(UserDto dto) {
        if (dto == null) return null;
        User user = new User();


        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAdmin(dto.isAdmin());

        return user;
    }


    public UserLoginDto toUserLoginDto(User user) {
        if (user == null) return null;
        UserLoginDto dto = new UserLoginDto();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }


    public User userLoginDtoToUser(UserLoginDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }


    public User registerDtoToUser(@Valid UserRegisterDto registerDto) {
        if (registerDto == null) return null;
        User user = new User();


        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());

        return user;
    }
}
