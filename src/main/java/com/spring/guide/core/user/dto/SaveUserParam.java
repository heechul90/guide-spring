package com.spring.guide.core.user.dto;

import com.spring.guide.core.user.domain.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
