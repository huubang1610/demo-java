package com.config.swagger.entities;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.utils.Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

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

    public enum Gender {
        MALE,
        FEMALE
    }

    public User form (UserRequest userRequest){
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .build();
    }
}
