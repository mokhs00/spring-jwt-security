package com.example.springjwtsecurity.repository;

import com.example.springjwtsecurity.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities") // Eager 조회로 조인해서 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
