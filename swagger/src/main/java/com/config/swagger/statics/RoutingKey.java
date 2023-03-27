package com.config.swagger.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoutingKey {
    SEND_EMAIL("send");
    private final String name;
}