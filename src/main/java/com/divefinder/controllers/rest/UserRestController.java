package com.divefinder.controllers.rest;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.UserDtoMapper;
import com.divefinder.models.User;
import com.divefinder.models.UserDto;
import com.divefinder.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    public UserRestController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        try{
            User user = userService.findUserById(id);
            return userDtoMapper.toDto(user);
        }catch (com.exceptions.EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    @PostMapping
        public UserDto createUser (@RequestBody UserDto dto){
            User user = userDtoMapper.toUser(dto);
            try {
                User createdUser = userService.createUser(user);
                return userDtoMapper.toDto(createdUser);
            } catch (EntityDuplicateException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }


}
