package com.config.swagger.validateRequest;

import org.springframework.validation.Validator;

import java.util.List;

public abstract class RequestValidator implements Validator {
    
    public abstract List<Class<?>> getSupportClasses();
    
    @Override
    public boolean supports(Class<?> clazz) {
        return getSupportClasses().stream().anyMatch(clazz::isAssignableFrom);
    }
}