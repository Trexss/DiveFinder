package com.divefinder.repositories;

import com.divefinder.models.User;

public interface UserRepository {
    User findUserById(int id);
    User findUserByEmail(String email);
    User createUser(User user);
}

