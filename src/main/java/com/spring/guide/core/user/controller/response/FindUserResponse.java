package com.spring.guide.core.user.controller.response;

import com.spring.guide.core.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindUserResponse {

    private Long userId;
    private String loginId;
    private String password;

    private String userName;
    private String userEmail;

    public FindUserResponse(User user) {
        this.userId = user.getId();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
    }
}
