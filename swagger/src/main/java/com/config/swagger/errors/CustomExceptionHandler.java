package com.config.swagger.errors;

import com.config.swagger.dto.Responses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Responses> handleException(ServiceException exception) {
      return Responses.notFound(exception.getCode(), exception.getMessage(),null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Responses> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return Responses.badRequest(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),errorMessages);
    }

}