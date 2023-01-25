package com.spring.guide.test.setup;

import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.SaveUserParam;
import com.spring.guide.core.user.dto.SearchCondition;
import com.spring.guide.core.user.dto.UpdateUserParam;
import com.spring.guide.core.user.dto.UserSearchCondition;
import org.springframework.data.domain.PageRequest;

public class UserSetup {

    //CREATE
    public static final Long USER_ID = 1L;
    public static final String LOGIN_ID = "spring";
    public static final String PASSWORD = "1234";
    public static final String NAME = "맹구";
    public static final String EMAIL = "mg@spring.com";

    //UPDATE
    public static final String UPDATE_NAME = "짱구";
    public static final String UPDATE_EMAIL = "jg@spring.com";

    //EXCEPTION
    public static final Long USER_NOT_FOUND_ID = 1L;
    public static final String USER_NOT_FOUND_MESSAGE = "존재하지 않는 유저입니다.";

    //URL
    public static final String API_FIND_USERS = "/api/v1/users";
    public static final String API_FIND_USER = "/api/v1/users/{userId}";
    public static final String API_SAVE_USER = "/api/v1/users";
    public static final String API_UPDATE_USER = "/api/v1/users/{userId}";
    public static final String API_DELETE_USER = "/api/v1/users/{userId}";


    public static User build() {
        return User.createUser()
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .name(NAME)
                .email(EMAIL)
                .build();
    }

    public static SaveUserParam saveUserParamBuild() {
        return SaveUserParam.builder()
                .loginId(LOGIN_ID)
                .password(PASSWORD)
                .name(NAME)
                .email(EMAIL)
                .build();
    }

    public static UpdateUserParam updateUserParamBuild() {
        return UpdateUserParam.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .build();
    }

    public static UserSearchCondition userSearchConditionBuild() {
        UserSearchCondition condition = new UserSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("구");
        return condition;
    }

    public static PageRequest pageRequestBuild() {
        return PageRequest.of(0, 10);
    }

    public static Long userId() {
        return USER_ID;
    }

    public static Long userNotFoundId() {
        return USER_NOT_FOUND_ID;
    }

    public static String userNotFoundMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
