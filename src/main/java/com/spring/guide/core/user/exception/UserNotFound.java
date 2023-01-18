package com.spring.guide.core.user.exception;

import com.spring.guide.global.exception.EntityNotFoundException;

public class UserNotFound extends EntityNotFoundException {

    public UserNotFound() {
        super("존재하지 않는 유저입니다.");
    }
}
