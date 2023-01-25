package com.spring.guide.core.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.guide.core.user.domain.User;
import com.spring.guide.core.user.service.UserService;
import com.spring.guide.global.json.JsonCode;
import com.spring.guide.test.IntegrationTest;
import com.spring.guide.test.setup.UserSetup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationTest {

    @Autowired protected UserService userService;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = UserSetup.build();
    }

    @Test
    @DisplayName("user 목록 조회")
    void findUsers() {
        //given

        //when

        //then
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
    void saveUser() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("user 수정")
    void updateUser() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("user 삭제")
    void deleteUser() {
        //given

        //when

        //then
    }
}