package com.spring.guide.core.user.controller.response;

import com.spring.guide.core.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserResponse {

    private Long updatedId;

    private String loginId;
    private String password;

    private String userName;
    private String userEmail;

    public UpdateUserResponse(User user) {
        this.updatedId = user.getId();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
    }
}
