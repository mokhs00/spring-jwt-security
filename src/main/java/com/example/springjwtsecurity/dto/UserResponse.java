package com.example.springjwtsecurity.dto;

import com.example.springjwtsecurity.domain.Authority;
import com.example.springjwtsecurity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {
    private Long id;

    private String username;

    private String nickname;

    private boolean activated;

    private Set<Authority> authorities;


    public UserResponse(final User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.activated = user.isActivated();
        this.authorities = user.getAuthorities();
    }
}
