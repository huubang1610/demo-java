package com.config.swagger.entities;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.utils.Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender ;
    @JsonSerialize(using = Serializer.SmplTimestampSerializer.class)
    @CreationTimestamp
    private Timestamp createdAt;
    @JsonSerialize(using = Serializer.SmplTimestampSerializer.class)
    @UpdateTimestamp
    private Timestamp updatedAt;
    private Timestamp deleteAt;
    private String userName;
    private String passWord;

    public enum Gender {
        MALE,
        FEMALE
    }

    public User form (UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .userName(userRequest.getUserName())
                .passWord(encode(userRequest.getPassWord()))
                .build();
    }
    public static String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    public static boolean matches(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashedPassword);
    }
}
