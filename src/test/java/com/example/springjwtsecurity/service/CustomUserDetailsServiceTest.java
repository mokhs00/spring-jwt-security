package com.example.springjwtsecurity.service;

import com.example.springjwtsecurity.domain.Authority;
import com.example.springjwtsecurity.domain.User;
import com.example.springjwtsecurity.repository.UserJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {


    @Mock
    private CustomUserDetailsService userDetailsService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @DisplayName("유저 이름으로 검색")
    @Test
    public void loadUserByUsername() {
        // given
        User user = new User(1L, "user", "pwd1", "mokhs", true,
                Set.of(new Authority("ROLE_USER")));

        // when

        doReturn(new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))))
                .when(userDetailsService).loadUserByUsername(anyString());

        // then
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());

        assertEquals(userDetails.getUsername(), user.getUsername());

    }
}