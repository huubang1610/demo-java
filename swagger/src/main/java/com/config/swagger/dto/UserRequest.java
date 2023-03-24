package com.config.swagger.dto;

import com.config.swagger.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    @NotNull(message = "Name không được để trống")
    private String name;
    @NotNull(message = "Email không được để trống")
    private String email;
    @NotNull(message = " Gender không được để trống")
    private User.Gender gender;
}
