package com.example.springjwtsecurity.controller;

import com.example.springjwtsecurity.dto.LoginCommand;
import com.example.springjwtsecurity.jwt.JwtAccessDeniedHandler;
import com.example.springjwtsecurity.jwt.JwtAuthenticationEntryPoint;
import com.example.springjwtsecurity.jwt.TokenProvider;
import com.example.springjwtsecurity.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthApi.class)
@AutoConfigureRestDocs
public class AuthApiWebMvcTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;



    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authorize() throws Exception {

        LoginCommand loginCommand = LoginCommand.builder()
                .username("admin")
                .password("admin")
                .build();


        when(this.authService.authorize(any(LoginCommand.class)))
                .thenReturn("jwt token");


        ResultActions resultActions = mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(loginCommand)));

        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth",
                        requestFields(
                                fieldWithPath("username").description("아이디"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("token").description("토큰")
                        )
                        )

                );


    }


}
