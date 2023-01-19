package com.spring.guide.core.user.controller;

import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.core.user.service.UserService;
import com.spring.guide.global.json.JsonCode;
import com.spring.guide.global.json.JsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping
    public JsonResponse findUsers(UserSearchCondition condition, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<User> content = userService.findUsers(condition, pageable);

        return JsonResponse.of(JsonCode.SUCCESS, null, messageSource);
    }
}
