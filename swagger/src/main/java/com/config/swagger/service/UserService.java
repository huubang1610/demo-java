package com.config.swagger.service;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.entities.User;
import com.config.swagger.errors.ServiceException;
import com.config.swagger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public User createUser(UserRequest userRequest) {
        return userRepository.save(new User().form(userRequest));
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw ServiceException.notFound("Not found");
        }
        return user;
    }

    public User update(Long id, UserRequest userRequest) {
        User user = getUserById(id);
        user.setGender(userRequest.getGender());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(getUserById(id).getId());
    }
}
