package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.domain.User;
import com.sega.game.transaction.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
