package com.example.springjwtsecurity.service;

import com.example.springjwtsecurity.repository.UserJpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public CustomUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userJpaRepository.findOneWithAuthoritiesByUsername(username)
                .map(user -> this.createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 해당 username의 User를 찾을 수 없습니다."));
    }


    private User createUser(String username, com.example.springjwtsecurity.domain.User user) {

        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 해당 username의 User는 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorityList = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), grantedAuthorityList);

    }
}
