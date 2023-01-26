package com.spring.guide.core.user.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.dto.SaveUserParam;
import com.spring.guide.core.user.dto.UpdateUserParam;
import com.spring.guide.core.user.dto.UserSearchCondition;
import com.spring.guide.core.user.repository.UserRepository;
import com.spring.guide.core.user.service.UserService;
import com.spring.guide.global.json.JsonCode;
import com.spring.guide.test.IntegrationTest;
import com.spring.guide.test.setup.UserSetup;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("user 통합 테스트")
class UserControllerTest extends IntegrationTest {

    @Autowired protected UserService userService;
    @Autowired protected UserRepository userRepository;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = UserSetup.build();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    @DisplayName("user_crud_성공_테스트")
    class successfulTest {

        @Test
        @DisplayName("user 목록 조회")
        void findUsers() throws Exception {
            //given
            userRepository.saveAll(UserSetup.usersBuild());

            UserSearchCondition condition = UserSetup.userSearchConditionBuild();
            LinkedMultiValueMap<String, String> conditionParams = new LinkedMultiValueMap<>();
            conditionParams.setAll(objectMapper.convertValue(condition, new TypeReference<Map<String, String>>() {}));

            PageRequest pageRequest = UserSetup.pageRequestBuild();
            LinkedMultiValueMap<String, String> pageRequestParams = new LinkedMultiValueMap<>();
            pageRequestParams.add("page", String.valueOf(pageRequest.getOffset()));
            pageRequestParams.add("size", String.valueOf(pageRequest.getPageSize()));

            //when
            ResultActions resultActions = mockMvc.perform(get(UserSetup.API_FIND_USERS)
                    .queryParams(conditionParams)
                    .queryParams(pageRequestParams));

            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transaction_time").isNotEmpty())
                    .andExpect(jsonPath("$.message").value(JsonCode.SUCCESS.name()))
                    .andExpect(jsonPath("$.status").value(JsonCode.SUCCESS.getStatus()))
                    .andExpect(jsonPath("$.code").value(JsonCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.length()", Matchers.is(10)))
                    .andDo(print());
        }

        @Test
        @DisplayName("user 단건 조회")
        void findUser() throws Exception {
            //given
            em.persist(user);

            //when
            ResultActions resultActions = mockMvc.perform(get(UserSetup.API_FIND_USER, user.getId()));

            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transaction_time").isNotEmpty())
                    .andExpect(jsonPath("$.message").value(JsonCode.SUCCESS.name()))
                    .andExpect(jsonPath("$.status").value(JsonCode.SUCCESS.getStatus()))
                    .andExpect(jsonPath("$.code").value(JsonCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.userId").value(user.getId()))
                    .andExpect(jsonPath("$.data.loginId").value(user.getLoginId()))
                    .andExpect(jsonPath("$.data.password").value(user.getPassword()))
                    .andExpect(jsonPath("$.data.userName").value(user.getName()))
                    .andExpect(jsonPath("$.data.userEmail").value(user.getEmail()))
                    .andDo(print());
        }

        @Test
        @DisplayName("user 저장")
        void saveUser() throws Exception {
            //given
            SaveUserParam param = UserSetup.saveUserParamBuild();

            //when
            ResultActions resultActions = mockMvc.perform(post(UserSetup.API_SAVE_USER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param)));

            //then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.transaction_time").isNotEmpty())
                    .andExpect(jsonPath("$.message").value(JsonCode.SUCCESS.name()))
                    .andExpect(jsonPath("$.status").value(JsonCode.SUCCESS.getStatus()))
                    .andExpect(jsonPath("$.code").value(JsonCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.savedId").isNotEmpty())
                    .andDo(print());
        }

        @Test
        @DisplayName("user 수정")
        void updateUser() throws Exception {
            //given
            em.persist(user);
            UpdateUserParam param = UserSetup.updateUserParamBuild();

            //when
            ResultActions resultActions = mockMvc.perform(put(UserSetup.API_UPDATE_USER, user.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(param)));

            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transaction_time").isNotEmpty())
                    .andExpect(jsonPath("$.message").value(JsonCode.SUCCESS.name()))
                    .andExpect(jsonPath("$.status").value(JsonCode.SUCCESS.getStatus()))
                    .andExpect(jsonPath("$.code").value(JsonCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data.updatedId").isNotEmpty())
                    .andDo(print());
        }

        @Test
        @DisplayName("user 삭제")
        void deleteUser() throws Exception {
            //given
            em.persist(user);

            //when
            ResultActions resultActions = mockMvc.perform(delete(UserSetup.API_DELETE_USER, user.getId()));

            //then
            resultActions
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.transaction_time").isNotEmpty())
                    .andExpect(jsonPath("$.message").value(JsonCode.SUCCESS.name()))
                    .andExpect(jsonPath("$.status").value(JsonCode.SUCCESS.getStatus()))
                    .andExpect(jsonPath("$.code").value(JsonCode.SUCCESS.getCode()))
                    .andExpect(jsonPath("$.data").isEmpty())
                    .andDo(print());
        }
    }
}