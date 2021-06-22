package com.example.springjwtsecurity.config;

import com.example.springjwtsecurity.jwt.JwtAccessDeniedHandler;
import com.example.springjwtsecurity.jwt.JwtAuthenticationEntryPoint;
import com.example.springjwtsecurity.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationentyPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationentyPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationentyPoint = jwtAuthenticationentyPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationentyPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청에 대한 접근 제한을 설정하겠다는 의미
                .antMatchers("/hello").permitAll() // 이 URL 패턴에는 인증 없이 접근 가능
                .antMatchers("/auth").permitAll()
                .antMatchers("/signup").permitAll()
                .anyRequest().authenticated() // 나머지는 인증되어야 접근 가능

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}
