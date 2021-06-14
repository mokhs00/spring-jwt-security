package com.example.springjwtsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Authority {

    @Id
    @Column(name = "authority_name")
    private String authorityName;
}
