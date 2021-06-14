package com.example.springjwtsecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring() // 아래 패턴 접근 시 시큐리티 로직 무시 가능
                .antMatchers(
                        "/h2-console/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // HttpServletRequest를 사용하는 요청에 대한 접근 제한을 설정하겠다는 의미
                .antMatchers("/hello").permitAll() // 이 URL 패턴에는 인증 없이 접근 가능
                .anyRequest().authenticated(); // 나머지는 인증되어야 접근 가능
    }
}
