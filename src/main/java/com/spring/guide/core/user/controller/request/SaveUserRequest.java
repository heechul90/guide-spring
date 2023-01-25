package com.spring.guide.core.user.controller.request;

import com.spring.guide.core.user.dto.SaveUserParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveUserRequest {

    private String loginId;
    private String password;

    private String userName;
    private String userEmail;

    public void validate() {

    }

    public SaveUserParam toParam() {
        return SaveUserParam.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.userName)
                .email(this.userEmail)
                .build();
    }
}
