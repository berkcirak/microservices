package com.customer.auth.service;

import com.customer.auth.model.User;
import com.customer.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public User getUserByUsername(String username){
        return userRepository.loadUserByUsername(username);
    }
}
