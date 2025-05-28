package com.example.authen_test.controller;

import com.example.authen_test.data.dto.AuthenDTO;
import com.example.authen_test.data.entity.AuthenEntity;
import com.example.authen_test.data.repository.AuthenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenController {
    private final AuthenRepository authenRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String index() {
        return "Hello World";
    }

    @GetMapping(value = "/admin")
    public String admin() {
        return "Hello Admin";
    }

    @PostMapping(value = "/join")
    public ResponseEntity<String> join(@RequestBody AuthenDTO authenDTO) {
        if(this.authenRepository.existsById(authenDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
        AuthenEntity authenEntity = AuthenEntity.builder()
                .username(authenDTO.getUsername())
                .password(passwordEncoder.encode(authenDTO.getPassword()))
                .role("ROLE_USER")
                .build();
        this.authenRepository.save(authenEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("가입성공!");
    }


}
