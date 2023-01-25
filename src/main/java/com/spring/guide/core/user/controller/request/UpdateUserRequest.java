package com.spring.guide.core.user.controller.request;

import com.spring.guide.core.user.dto.UpdateUserParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserRequest {

    private String userName;
    private String userEmail;

    public void validate() {

    }

    public UpdateUserParam toParam() {
        return UpdateUserParam.builder()
                .name(this.userName)
                .email(this.userEmail)
                .build();
    }
}
