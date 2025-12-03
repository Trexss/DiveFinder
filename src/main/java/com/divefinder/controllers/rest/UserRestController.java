package com.divefinder.controllers.rest;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.UserDtoMapper;
import com.divefinder.models.User;
import com.divefinder.models.UserDto;
import com.divefinder.models.UserRestResponseDto;
import com.divefinder.services.UserService;
import com.exceptions.AuthorizationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;
    private final AuthenticationHelper authenticationHelper;

    public UserRestController(UserService userService, UserDtoMapper userDtoMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/{id}")
    public UserRestResponseDto getUserById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try{
            User requestor = authenticationHelper.tryGetUser(headers);
            if(!requestor.isAdmin()){
                throw new AuthorizationException("Not authorized");
            }
            User user = userService.findUserById(id);
            return userDtoMapper.toUserRestResponseDto(user);
        }catch (com.exceptions.EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (com.exceptions.AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }
    @PostMapping
        public UserDto createUser (@RequestBody UserDto dto){
            User user = userDtoMapper.userDtoToUser(dto);
            try {
                User createdUser = userService.createUser(user);
                return userDtoMapper.toUserDto(createdUser);
            } catch (EntityDuplicateException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }


}
