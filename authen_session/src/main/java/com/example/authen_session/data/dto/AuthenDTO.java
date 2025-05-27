package com.example.authen_session.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenDTO {
    private String username; // id는 username 으로 고정이다.
    private String password; // pw는 password 로 고정이다.
}
