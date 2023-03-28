package com.config.swagger.service;

import com.config.swagger.dto.LoginRequest;
import com.config.swagger.dto.UserDetails;
import com.config.swagger.dto.UserRequest;
import com.config.swagger.dto.redisHash.UserRedis;
import com.config.swagger.entities.User;
import com.config.swagger.errors.ServiceException;
import com.config.swagger.message.Message;
import com.config.swagger.message.MessageProducer;
import com.config.swagger.repository.UserRepository;
import com.config.swagger.statics.Exchange;
import com.config.swagger.statics.RoutingKey;
import com.config.swagger.utils.ListResult;
import com.config.swagger.utils.PageableUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.config.swagger.utils.Constant.KEY_ALL_USERS;
import static com.config.swagger.utils.Constant.NOT_FOUND;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, List<UserRedis>> redisTemplate;
    private final MessageProducer messageProducer;

    public UserDetails login (LoginRequest loginRequest){
        return UserDetails.builder().build();
    }

    public User createUser(UserRequest userRequest) {
        messageProducer.sendEmail(Message.form(userRequest.getEmail()), Exchange.ACTION_USER.getName(), RoutingKey.SEND_EMAIL.getName());
        return userRepository.save(new User().form(userRequest));
    }

    public ListResult<UserRedis> getAllUsers(int page, int size, String orderBy, boolean desc) {
        List<UserRedis> users = redisTemplate.opsForValue().get(KEY_ALL_USERS);
        if (users == null) {
            users = userRepository.findAllToPage(PageableUtils.pageable(page, size, orderBy, desc)).getContent().stream()
                    .map(UserRedis::from)
                    .collect(Collectors.toList());
            redisTemplate.opsForValue().set(KEY_ALL_USERS, users, 10, TimeUnit.SECONDS);
        }
        return ListResult.from(PageableUtils.convert(users, (PageRequest) PageableUtils.pageable(page, size, orderBy, desc)));
    }

    public ListResult<UserRedis> findByName(String name, int page, int size, String orderBy, boolean desc) {
        if (name == null) {
            return getAllUsers(page, size, orderBy, desc);
        }
        List<UserRedis> users = redisTemplate.opsForValue().get(name);
        if (users == null) {
            String query = "%" + name + "%";
            users = userRepository.findByName(query).stream()
                    .map(UserRedis::from)
                    .collect(Collectors.toList());
            redisTemplate.opsForValue().set(name, users, 10, TimeUnit.SECONDS);
        }
        return ListResult.from(PageableUtils.convert(users, (PageRequest) PageableUtils.pageable(page, size, orderBy, desc)));
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw ServiceException.notFound(NOT_FOUND);
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
