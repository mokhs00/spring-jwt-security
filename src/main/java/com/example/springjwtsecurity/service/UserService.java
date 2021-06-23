package com.example.springjwtsecurity.service;

import com.example.springjwtsecurity.domain.Authority;
import com.example.springjwtsecurity.domain.User;
import com.example.springjwtsecurity.dto.RegisterCommand;
import com.example.springjwtsecurity.dto.UserResponse;
import com.example.springjwtsecurity.repository.UserJpaRepository;
import com.example.springjwtsecurity.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse signup(RegisterCommand dto) {
        if (this.userJpaRepository.findOneWithAuthoritiesByUsername(dto.getUsername())
                .isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return new UserResponse(this.userJpaRepository.save(user));
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(final String username) {
        return this.userJpaRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userJpaRepository::findOneWithAuthoritiesByUsername);
    }
}
