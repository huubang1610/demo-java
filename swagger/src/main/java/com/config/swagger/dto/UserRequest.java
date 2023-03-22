package com.config.swagger.dto;

import com.config.swagger.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    private String name;
    private String email;
    private User.Gender gender ;
}
