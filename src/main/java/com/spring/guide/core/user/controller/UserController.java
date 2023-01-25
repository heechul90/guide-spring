package com.spring.guide.core.user.controller;

import com.spring.guide.core.user.controller.request.SaveUserRequest;
import com.spring.guide.core.user.controller.request.UpdateUserRequest;
import com.spring.guide.core.user.controller.response.FindUserResponse;
import com.spring.guide.core.user.controller.response.SaveUserResponse;
import com.spring.guide.core.user.controller.response.UpdateUserResponse;
import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.core.user.service.UserService;
import com.spring.guide.global.json.JsonCode;
import com.spring.guide.global.json.JsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * user 목록 조회
     */
    @GetMapping
    public JsonResponse findUsers(UserSearchCondition condition, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<User> content = userService.findUsers(condition, pageable);
        List<FindUserResponse> users = content.getContent().stream()
                .map(FindUserResponse::new)
                .collect(Collectors.toList());
        return JsonResponse.of(JsonCode.SUCCESS, users);
    }

    /**
     * user 단건 조회
     */
    @GetMapping("/{userId}")
    public JsonResponse findUser(@PathVariable("userId") Long userId) {
        User findUser = userService.findUser(userId);
        FindUserResponse user = new FindUserResponse(findUser);
        return JsonResponse.of(JsonCode.SUCCESS, user);
    }

    /**
     * user 저장
     */
    @PostMapping
    public JsonResponse saveUser(@RequestBody @Validated SaveUserRequest request) {
        //validate
        request.validate();

        User savedUser = userService.saveUser(request.toParam());
        return JsonResponse.of(JsonCode.SUCCESS, new SaveUserResponse(savedUser.getId()));
    }

    /**
     * user 수정
     */
    @PutMapping("/{userId}")
    public JsonResponse updateUser(@PathVariable("userId") Long userId, @RequestBody @Validated UpdateUserRequest request) {
        //validate
        request.validate();

        userService.updateUser(userId, request.toParam());
        User updatedUser = userService.findUser(userId);

        return JsonResponse.of(JsonCode.SUCCESS, new UpdateUserResponse(updatedUser));
    }

    /**
     * user 삭제
     */
    @DeleteMapping("/{userId}")
    public JsonResponse deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return JsonResponse.of(JsonCode.SUCCESS);
    }
}
