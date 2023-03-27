package com.config.swagger.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Exchange {
    ACTION_USER("ACTION_USER");
    private final String name;
}