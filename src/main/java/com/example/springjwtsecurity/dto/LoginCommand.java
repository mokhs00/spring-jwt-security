package com.example.springjwtsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {

    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

}



