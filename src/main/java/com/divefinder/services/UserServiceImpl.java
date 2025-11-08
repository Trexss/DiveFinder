package com.divefinder.services;

import com.divefinder.models.User;
import com.divefinder.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        // checks if User with the same email exists
        try {
            userRepository.findUserByEmail(user.getEmail());
            throw new com.divefinder.exceptions.EntityDuplicateException("User with email " + user.getEmail() + " already exists.");
        } catch (com.exceptions.EntityNotFoundException e) {
            return userRepository.createUser(user);
        }
    }
}
