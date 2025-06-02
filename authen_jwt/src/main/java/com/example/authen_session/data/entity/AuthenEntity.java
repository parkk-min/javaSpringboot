package com.example.authen_session.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "authenticationtbl")
public class AuthenEntity {
    @Id
    private String username; // id는 username 으로 고정이다.

    @Column(nullable = false)
    private String password; // pw는 password 로 고정이다.

    @Column(nullable = false)
    private String role;
}
