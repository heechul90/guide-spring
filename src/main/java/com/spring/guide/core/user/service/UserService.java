package com.spring.guide.core.user.service;

import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.SaveUserParam;
import com.spring.guide.core.user.dto.UpdateUserParam;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.core.user.repository.UserQueryRepository;
import com.spring.guide.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserQueryRepository userQueryRepository;
    private final UserRepository userRepository;

    /**
     * user 목록 조회
     */
    public Page<User> findUsers(UserSearchCondition condition, Pageable pageable) {
        return userQueryRepository.findUsers(condition, pageable);
    }

    /**
     * user 단건 조회
     */
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElse(null);
    }

    /**
     * user 저장
     */
    @Transactional
    public User saveUser(SaveUserParam param) {
        return userRepository.save(param.toUser());
    }

    /**
     * user 수정
     */
    @Transactional
    public void updateUser(Long userId, UpdateUserParam param) {
        User findUser = userRepository.findById(userId)
                .orElse(null);
        findUser.updateUser(param);
    }

    /**
     * user 삭제
     */
    @Transactional
    public void deleteUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElse(null);
        userRepository.delete(findUser);
    }
}
