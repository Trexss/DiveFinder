package com.divefinder.services;

import com.divefinder.models.User;

public interface UserService {
    User findUserById(int id);
    User findUserByEmail(String email);
    User createUser(User user);
}
