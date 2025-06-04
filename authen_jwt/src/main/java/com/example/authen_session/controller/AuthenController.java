package com.example.authen_session.controller;

import com.example.authen_session.data.dto.AuthenDTO;
import com.example.authen_session.data.entity.AuthenEntity;
import com.example.authen_session.data.repository.AuthenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenRepository authenRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping(value = "/admin")
    public String admin() {
        return "hello admin";
    }

    @PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody AuthenDTO authenDTO) {
        if(this.authenRepository.existsById(authenDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }

        AuthenEntity authenEntity = AuthenEntity.builder()
                .username(authenDTO.getUsername())
                .password(this.passwordEncoder.encode(authenDTO.getPassword()))
                .role("ROLE_USER").build();
        this.authenRepository.save(authenEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("가입성공.");
    }
}
