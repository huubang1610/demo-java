package com.config.swagger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Responses {
    private Integer code;
    private String status;
    private Object data;

    public static ResponseEntity<Responses> notFound(Integer code, String status, Object data) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Responses(code,status,data));
    }
    public static ResponseEntity<Responses> badRequest(Integer code, String status, Object data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses(code,status,data));
    }
}
