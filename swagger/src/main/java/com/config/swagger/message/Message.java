package com.config.swagger.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    @JsonProperty("email")
    private String emailAddress;

    public static Message form (String emailAddress) {
        return Message.builder().emailAddress(emailAddress).build();
    }
}
