package com.config.swagger.dto.redisHash;

import com.config.swagger.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRedis implements Serializable {
  
    private String name;
    private User.Gender gender;
    private String email;

    public static UserRedis from (User user){
        return UserRedis.builder()
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .build();
    }
}