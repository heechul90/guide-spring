package com.spring.guide.core.user.repository;

import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.test.RepositoryTest;
import com.spring.guide.test.config.UserTestConfig;
import com.spring.guide.test.setup.UserSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(UserTestConfig.class)
class UserRepositoryTest extends RepositoryTest {

    @Autowired protected UserQueryRepository userQueryRepository;
    @Autowired protected UserRepository userRepository;

    @Nested
    @DisplayName("user 레포지토리 성공 테스트")
    class SuccessfulTest {

        @Test
        @DisplayName("user 목록 조회")
        void findUsers() {
            //given
            List<User> users = UserSetup.usersBuild();
            userRepository.saveAll(users);

            UserSearchCondition condition = UserSetup.userSearchConditionBuild();
            PageRequest pageRequest = UserSetup.pageRequestBuild();

            //when
            Page<User> content = userQueryRepository.findUsers(condition, pageRequest);

            //then
            assertThat(content.getTotalElements()).isEqualTo(10);
            assertThat(content.getContent().size()).isEqualTo(10);
        }
    }
}