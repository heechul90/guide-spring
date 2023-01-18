package com.spring.guide.global.exception;

import com.spring.guide.global.exception.dto.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
