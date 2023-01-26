package com.spring.guide.core.user.service;

import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.SaveUserParam;
import com.spring.guide.core.user.dto.UpdateUserParam;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.core.user.exception.UserNotFound;
import com.spring.guide.core.user.repository.UserQueryRepository;
import com.spring.guide.core.user.repository.UserRepository;
import com.spring.guide.test.MockTest;
import com.spring.guide.test.setup.UserSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UserServiceTest extends MockTest {

    @InjectMocks protected UserService userService;
    @Mock protected UserQueryRepository userQueryRepository;
    @Mock protected UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = UserSetup.build();
    }

    @Nested
    @DisplayName("성공 테스트")
    class SuccessfulTest {

        @Test
        @DisplayName("user 목록 조회")
        void findUsers() {
            //given
            given(userQueryRepository.findUsers(any(UserSearchCondition.class), any(Pageable.class))).willReturn(new PageImpl<>(List.of(user)));

            //when
            Page<User> content = userQueryRepository.findUsers(UserSetup.userSearchConditionBuild(), UserSetup.pageRequestBuild());

            //then
            assertThat(content.getTotalElements()).isEqualTo(1);
            assertThat(content.getContent().size()).isEqualTo(1);

            //verify
            verify(userQueryRepository, times(1)).findUsers(any(UserSearchCondition.class), any(Pageable.class));
        }

        @Test
        @DisplayName("user 단건 조회")
        void findUser() {
            //given
            given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));

            //when
            User findUser = userService.findUser(UserSetup.USER_ID);

            //then
            assertThat(findUser.getLoginId()).isEqualTo(user.getLoginId());
            assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
            assertThat(findUser.getName()).isEqualTo(user.getName());
            assertThat(findUser.getEmail()).isEqualTo(user.getEmail());

            //verify
            verify(userRepository, times(1)).findById(any(Long.class));
        }

        @Test
        @DisplayName("user 저장")
        void saveUser() {
            //given
            given(userRepository.save(any(User.class))).willReturn(user);
            SaveUserParam param = UserSetup.saveUserParamBuild();

            //when
            User savedUser = userService.saveUser(param);

            //then
            assertThat(savedUser.getLoginId()).isEqualTo(user.getLoginId());
            assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
            assertThat(savedUser.getName()).isEqualTo(user.getName());
            assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());

            //verify
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("user 수정")
        void updateUser() {
            //given
            given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
            UpdateUserParam param = UserSetup.updateUserParamBuild();

            //when
            userService.updateUser(UserSetup.USER_ID, param);

            //then
            assertThat(user.getName()).isEqualTo(param.getName());
            assertThat(user.getEmail()).isEqualTo(param.getEmail());

            //verify
            verify(userRepository, times(1)).findById(any(Long.class));
        }

        @Test
        @DisplayName("user 삭제")
        void deleteUser() {
            //given
            given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));

            //when
            userService.deleteUser(UserSetup.USER_ID);

            //then

            //verify
            verify(userRepository, times(1)).findById(any(Long.class));
            verify(userRepository, times(1)).delete(any(User.class));
        }
    }

    @Nested
    @DisplayName("예외 발생 테스트")
    class ExceptionTest {

        @Test
        @DisplayName("user 단건 조회_예외")
        void findUser_userNotFound() {
            //given
            given(userRepository.findById(any(Long.class))).willThrow(new UserNotFound());

            //expected
            assertThatThrownBy(() -> userService.findUser(UserSetup.USER_NOT_FOUND_ID))
                    .isInstanceOf(UserNotFound.class)
                    .hasMessage(UserSetup.USER_NOT_FOUND_MESSAGE);
        }

        @Test
        @DisplayName("user 수정_예외")
        void updateUser_userNotFound() {
            //given
            given(userRepository.findById(any(Long.class))).willThrow(new UserNotFound());

            //expected
            assertThatThrownBy(() -> userService.updateUser(UserSetup.USER_NOT_FOUND_ID, UserSetup.updateUserParamBuild()))
                    .isInstanceOf(UserNotFound.class)
                    .hasMessage(UserSetup.USER_NOT_FOUND_MESSAGE);
        }

        @Test
        @DisplayName("user 삭제_예외")
        void deleteUser_userNotFound() {
            //given
            given(userRepository.findById(any(Long.class))).willThrow(new UserNotFound());

            //expected
            assertThatThrownBy(() -> userService.deleteUser(UserSetup.USER_NOT_FOUND_ID))
                    .isInstanceOf(UserNotFound.class)
                    .hasMessage(UserSetup.USER_NOT_FOUND_MESSAGE);
        }
    }
}