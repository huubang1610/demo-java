package com.config.swagger.config;

import com.config.swagger.validateRequest.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class ControllerAdvisor {

    private final List<RequestValidator> validators;
    private Map<Class<?>, RequestValidator> validatorMap;

    @PostConstruct
    public void postConstruct() {
        validators.forEach(validator -> {
            validator.getSupportClasses().forEach(clazz -> validatorMap.putIfAbsent(clazz, validator));
        });
    }

    @InitBinder
    public void initAutoTrimmingString(WebDataBinder binder) {
    }
}