package com.spring.guide.core.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchCondition {

    private SearchCondition searchCondition;
    private String searchKeyword;
}
