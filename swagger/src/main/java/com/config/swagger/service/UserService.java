package com.config.swagger.service;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.entities.User;
import com.config.swagger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public User createUser(UserRequest userRequest){
        return userRepository.save(new User().form(userRequest));
    }
}
