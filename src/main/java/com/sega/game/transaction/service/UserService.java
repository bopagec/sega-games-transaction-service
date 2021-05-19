package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }
}
