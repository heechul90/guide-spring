package com.spring.guide.core.user.dto;

import com.spring.guide.core.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveUserParam {

    private String loginId;
    private String password;

    private String name;
    private String email;

    public User toUser() {
        return User.createUser()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
