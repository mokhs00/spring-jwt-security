package com.example.springjwtsecurity.controller;


import com.example.springjwtsecurity.domain.User;
import com.example.springjwtsecurity.dto.RegisterCommand;
import com.example.springjwtsecurity.dto.UserResponse;
import com.example.springjwtsecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping
public class UserApi {


    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody RegisterCommand dto) {
        return ResponseEntity.ok(this.userService.signup(dto));
    }


    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getMyUserInfo() {
        Optional<User> userWithAuthorities = this.userService.getUserWithAuthorities();

        if (userWithAuthorities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserResponse(userWithAuthorities.get()));
    }
}
