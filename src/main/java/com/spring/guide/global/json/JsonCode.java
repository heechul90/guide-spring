package com.spring.guide.global.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JsonCode {

    SUCCESS(200, "S001", "success"),
    CREATED(201, "S002", "created")
    ;

    private final int status;
    private final String code;
    private final String message;
}
