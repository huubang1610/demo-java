package com.config.swagger.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceException extends RuntimeException {

    private Integer code;
    private HttpStatus status;
    private String message;


    public ServiceException(String message, Integer code, HttpStatus status) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public static ServiceException notFound(String message) {
        return new ServiceException(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND,message);
    }

    public static ServiceException forbidden(String message) {
        return new ServiceException(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN,message);
    }

    public static ServiceException badRequest(String message) {
        return new ServiceException(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST,message);
    }
}