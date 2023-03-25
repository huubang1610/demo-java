package com.config.swagger.service;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.dto.redisHash.UserRedis;
import com.config.swagger.entities.User;
import com.config.swagger.errors.ServiceException;
import com.config.swagger.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, List<UserRedis>> redisTemplate;
    private final String KEY_ALL_USERS = "all_users";


    public User createUser(UserRequest userRequest) {
        return userRepository.save(new User().form(userRequest));
    }

    public List<UserRedis> getAllUsers() {
        List<UserRedis> users = redisTemplate.opsForValue().get(KEY_ALL_USERS);
        if (users == null) {
            users = userRepository.findAll().stream()
                    .map(UserRedis::from)
                    .collect(Collectors.toList());
            redisTemplate.opsForValue().set(KEY_ALL_USERS, users, 10, TimeUnit.SECONDS);
        }
        return users;
    }

    public List<UserRedis> findByName(String name) {
        if (name == null){
            return getAllUsers();
        }
        List<UserRedis> users = redisTemplate.opsForValue().get(name);
        if (users == null) {
            String query = "%"+name+"%";
            users = userRepository.findByName(query).stream()
                    .map(UserRedis::from)
                    .collect(Collectors.toList());
            redisTemplate.opsForValue().set(name, users, 10, TimeUnit.SECONDS);
        }
        return users;
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
